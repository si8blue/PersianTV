package ir.persiantv.data

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {

    private const val PREFS_NAME = "persian_tv_prefs"
    private const val KEY_LAST_CHANNEL_ID = "last_channel_id"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveLastChannelId(context: Context, channelId: Int) {
        getPrefs(context).edit().putInt(KEY_LAST_CHANNEL_ID, channelId).apply()
    }

    fun getLastChannelId(context: Context): Int {
        return getPrefs(context).getInt(KEY_LAST_CHANNEL_ID, -1)
    }

    fun saveTheme(context: Context, isDark: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_IS_DARK_THEME, isDark).apply()
    }

    fun isDarkTheme(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_DARK_THEME, true)
    }
}
