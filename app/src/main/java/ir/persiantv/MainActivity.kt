package ir.persiantv

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ir.persiantv.data.Channel
import ir.persiantv.ui.channel.ChannelListPanel
import ir.persiantv.ui.player.PlayerScreen
import ir.persiantv.ui.settings.SettingsPanel
import ir.persiantv.ui.theme.*
import ir.persiantv.ui.viewmodel.ChannelViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ChannelViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            PersianTVTheme(isDarkTheme = uiState.isDarkTheme) {
                PersianTVApp(
                    uiState = uiState,
                    onChannelClick = viewModel::selectChannel,
                    onBack = viewModel::stopPlayback,
                    onThemeToggle = viewModel::toggleTheme,
                    onExit = { finish() }
                )
            }
        }
    }
}

@Composable
fun PersianTVApp(
    uiState: ir.persiantv.ui.viewmodel.ChannelUiState,
    onChannelClick: (Channel) -> Unit,
    onBack: () -> Unit,
    onThemeToggle: () -> Unit,
    onExit: () -> Unit
) {
    val context = LocalContext.current

    if (uiState.isPlaying && uiState.selectedChannel != null) {
        PlayerScreen(
            channel = uiState.selectedChannel,
            onBack = onBack
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Live TV",
                    style = TitleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                ChannelListPanel(
                    channels = uiState.channels,
                    selectedChannel = uiState.selectedChannel,
                    onChannelClick = onChannelClick,
                    modifier = Modifier.weight(2f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                SettingsPanel(
                    isDarkTheme = uiState.isDarkTheme,
                    onThemeToggle = onThemeToggle,
                    onExit = onExit,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
