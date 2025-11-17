import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lusonus.data.model.MenuItem
import com.example.lusonus.navigation.LocalNavController
import com.example.lusonus.navigation.Routes
import com.example.lusonus.ui.composables.Layout.MainLayout
import com.example.lusonus.ui.composables.Layout.SearchAndSort.SearchAndSort
import com.example.lusonus.ui.screens.FolderViewScreen.FolderLibraryContent
import com.example.lusonus.ui.screens.folders.FolderLibraryViewModel
import com.example.lusonus.ui.utils.getFolderName
import com.example.lusonus.ui.utils.getMediaUrisInFolder
import com.example.organisemedia.Layout.FloatingActionButton.SharedFloatingActionButton

@Composable
fun FolderLibraryScreen() {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val viewModel: FolderLibraryViewModel = viewModel(LocalNavController.current.context as ComponentActivity)

    val folders = viewModel.getAllFolders()

    val pickFolderLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
            uri?.let { folderUri ->

                // Persist permission so we can read files long-term
                context.contentResolver.takePersistableUriPermission(
                    folderUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                // ---- Scan folder and get media inside ----
                val mediaUris = context.getMediaUrisInFolder(folderUri)

                // Folder name = last path segment or “Folder”
                val folderName = context.getFolderName(folderUri)

                viewModel.addFolder(
                    context = context,
                    folderUri = folderUri,
                    folderName = folderName,
                    folderMediaUris = mediaUris
                )
            }
        }

    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchInfo by rememberSaveable { mutableStateOf("") }

    MainLayout(
        content = {

            val sortOptions = listOf(
                MenuItem("Alphabetical") { viewModel.sortFolders("alphabetically") }
            )

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

            FolderLibraryContent(
                folders = folders,
                onDeleteFolder = { folderName -> viewModel.deleteFolder(folderName) },
                onClickFolder = { folderName ->
                    navController.navigate(Routes.Folder.createRoute(folderName))
                }
            )
        },

        screenTitle = "Folders",
        floatingActionButton = {
            SharedFloatingActionButton(
                onClick = { pickFolderLauncher.launch(null) }
            )
        }
    )
}
