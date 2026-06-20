package ir.persiantv.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.persiantv.ui.theme.*

@Composable
fun SettingsPanel(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Settings",
            style = TitleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsButton(
            text = if (isDarkTheme) "Light" else "Dark",
            icon = if (isDarkTheme) Icons.Default.Brightness7 else Icons.Default.Brightness4,
            onClick = onThemeToggle
        )

        Spacer(modifier = Modifier.weight(1f))

        SettingsButton(
            text = "Exit",
            icon = Icons.Default.ExitToApp,
            onClick = onExit,
            isDestructive = true
        )
    }
}

@Composable
private fun SettingsButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    val backgroundColor = when {
        isFocused && isDestructive -> PersianRed.copy(alpha = 0.2f)
        isFocused -> PersianBlue.copy(alpha = 0.15f)
        else -> Color.Transparent
    }

    val contentColor = when {
        isDestructive -> PersianRed
        else -> MaterialTheme.colorScheme.onSurface
    }

    val borderColor = when {
        isFocused && isDestructive -> PersianRed
        isFocused -> FocusGlow.copy(alpha = 0.7f)
        else -> Color.Transparent
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .focusable()
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.DirectionCenter || keyEvent.key == Key.Enter) {
                    onClick()
                    true
                } else {
                    false
                }
            },
        color = backgroundColor,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = if (isFocused) 2.dp else 0.dp,
            color = borderColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = text,
                style = LabelLarge.copy(color = contentColor),
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
