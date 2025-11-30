import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.composables.Layout.TopBar.SharedNavTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.SharedTopBar
import com.example.lusonus.ui.composables.Layout.TopBar.TopBarAddButton
import com.example.lusonus.ui.screens.FolderViewScreen.FolderLibraryContent
import com.example.lusonus.ui.screens.FolderViewScreen.FolderLibraryViewModel
import com.example.lusonus.ui.utils.getName
import com.example.lusonus.ui.utils.scanFolderRecursive
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FolderLibraryScreen() {
    // Gets the nav controller of the app.
    val navController = LocalNavController.current

    // Gets the local context.
    val context = LocalContext.current

    // Gets the application (super important for updating media in folders)
    val app = context.applicationContext as Application

    // Gets the viewmodel for this view.
    val viewModel: FolderLibraryViewModel = viewModel(viewModelStoreOwner = LocalNavController.current.context as ComponentActivity)

    // DOES THE HOT FLOW!!!! YES!
    val folders by viewModel.folders.collectAsState()

    // Coroutine scope used by folders!
    val coroutineScope = rememberCoroutineScope()

    // The picker where you select a folder.
    val pickFolderLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree())
        {
            uri: Uri? ->
            // Tried to just return but android studio forced this so...
            uri ?: return@rememberLauncherForActivityResult

            // Persist permission so we can read files long-term. (really important for updating)
            context.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            // Starts coroutine.
            coroutineScope.launch {
                // Gets media from the folder tree, it's really interesting go read it in FileUtils.kt
                // Uses a coroutine!!!
                val mediaUris = withContext(Dispatchers.IO) {
                    context.scanFolderRecursive(uri)
                }

                // Folder name = last path segment or “Folder”, it's also in FileUtils.kt
                val folderName = context.getName(uri).substringAfterLast("/")

                // Adds the folder to the viewmodel
                viewModel.addFolder(
                    context = context,
                    folderUri = uri,
                    folderName = folderName,
                    folderMediaUris = mediaUris
                )
            }

        }

    // This is for search and sort.
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }

    // This is in charge of managing when the user leaves the app on this page and comes back to this page.
    val lifecycleOwner = LocalLifecycleOwner.current

    // It refreshes when the screen appears and when the app returns to the foreground.
    DisposableEffect(lifecycleOwner) {
        // Observer to check for the comeback... haha
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // User returned to the app while screen is active.
                folders.forEach { folder ->
                    // Refreshes folders.
                    viewModel.refreshFolder(context, folder.uri)
                }
            }
        }

        // Adds the observer.
        lifecycleOwner.lifecycle.addObserver(observer)

        // When it's job is done.
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // The main layout (the actual visuals)
    MainLayout(
        content = {
            // When the composition is entered, we reload the folders (checks for updates!).
            LaunchedEffect(Unit) {
                folders.forEach { folder ->
                    // Refreshes folders. So much better with flows.
                    viewModel.refreshFolder(context, folder.uri)
                }
            }

            // This is the sort options menu.
            val sortOptions = listOf(
                MenuItem("Alphabetical") { viewModel.sortFolders("alphabetically") },
                MenuItem("Date Added") { viewModel.sortFolders("date added") },
                MenuItem("Last Played") { viewModel.sortFolders("last played") }
            )

            // Calls the search and sort.
            SearchAndSort(
                sortOptions,
                expanded,
                { expanded = !it },
                searchInfo,
                {
                    searchInfo = it
                    viewModel.searchFolders(it.lowercase())
                }
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).offset(y = 40.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                // Calls the displaying composables for the folders themselves.
                FolderLibraryContent(
                    folders = folders,
                    onDeleteFolder = { folderName -> viewModel.deleteFolder(folderName) },
                    onClickFolder = { folderName ->
                        navController.navigate(Routes.Folder.createRoute(folderName))
                    }
                )
            }
        },
        screenTitle = "Lusonus",

        topBar = {
            Column {
                SharedTopBar("Lusonus", {
                    TopBarAddButton(onClick = {
                        pickFolderLauncher.launch(null)
                    } )
                })
                SharedNavTopBar()
            }
        }
    )
}
