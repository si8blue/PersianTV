package ir.persiantv.ui.channel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.persiantv.data.Channel
import ir.persiantv.ui.theme.*

@Composable
fun ChannelListPanel(
    channels: List<Channel>,
    selectedChannel: Channel?,
    onChannelClick: (Channel) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedChannel) {
        selectedChannel?.let { channel ->
            val index = channels.indexOf(channel)
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Channels",
            style = TitleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textAlign = TextAlign.Start
        )

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(channels) { index, channel ->
                ChannelListItem(
                    channel = channel,
                    isSelected = channel.id == selectedChannel?.id,
                    isInitialFocus = index == 0 && selectedChannel == null,
                    onChannelClick = onChannelClick
                )
            }
        }
    }
}
