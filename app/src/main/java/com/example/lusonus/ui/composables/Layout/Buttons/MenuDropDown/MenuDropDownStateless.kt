import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lusonus.data.model.classes.MenuItem

@Composable
fun MinimalDropdownMenu(
    menuItems: List<MenuItem>,
    expanded: Boolean,
    toggleAction: (Boolean) -> Unit,
    icon: ImageVector
) {
    Box {
        IconButton(
            onClick = { toggleAction(expanded) }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "More options.",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { toggleAction(expanded) }
        ) {
            menuItems.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.title) },
                    onClick = {
                        option.action()
                        toggleAction(expanded)
                    }
                )
            }
        }
    }
}