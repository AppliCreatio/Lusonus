import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lusonus.R
import com.example.lusonus.ui.utils.getFileName

@Composable
fun FileRow(
    uri: Uri,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val fullName = remember(uri) {
        runCatching { context.getFileName(uri) }.getOrElse { "Unknown" }
    }
    val cleanName = remember(fullName) { fullName.substringBeforeLast(".") }

    // Load bitmap from embedded artwork using MediaMetadataRetriever
    val bitmap = remember(uri) {
        runCatching {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(context, uri)
            val art = mmr.embeddedPicture
            mmr.release()
            art?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
        }.getOrNull()
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "File artwork",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.lusonus_placeholder),
                contentDescription = "File artwork",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
        ) {
            Text(
                text = cleanName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
