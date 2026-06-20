package ir.persiantv.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ir.persiantv.data.Channel
import ir.persiantv.data.ChannelRepository
import ir.persiantv.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChannelUiState(
    val channels: List<Channel> = emptyList(),
    val selectedChannel: Channel? = null,
    val isPlaying: Boolean = false,
    val isDarkTheme: Boolean = true,
    val isLoading: Boolean = true
)

class ChannelViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ChannelUiState())
    val uiState: StateFlow<ChannelUiState> = _uiState.asStateFlow()

    init {
        loadChannels()
        loadTheme()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val channels = ChannelRepository.getChannels(context)
            val lastChannelId = PreferencesManager.getLastChannelId(context)
            val selectedChannel = channels.find { it.id == lastChannelId }

            _uiState.value = _uiState.value.copy(
                channels = channels,
                selectedChannel = selectedChannel,
                isLoading = false
            )
        }
    }

    private fun loadTheme() {
        val context = getApplication<Application>()
        val isDark = PreferencesManager.isDarkTheme(context)
        _uiState.value = _uiState.value.copy(isDarkTheme = isDark)
    }

    fun selectChannel(channel: Channel) {
        val context = getApplication<Application>()
        PreferencesManager.saveLastChannelId(context, channel.id)
        _uiState.value = _uiState.value.copy(
            selectedChannel = channel,
            isPlaying = true
        )
    }

    fun stopPlayback() {
        _uiState.value = _uiState.value.copy(isPlaying = false)
    }

    fun toggleTheme() {
        val newTheme = !_uiState.value.isDarkTheme
        val context = getApplication<Application>()
        PreferencesManager.saveTheme(context, newTheme)
        _uiState.value = _uiState.value.copy(isDarkTheme = newTheme)
    }
}
