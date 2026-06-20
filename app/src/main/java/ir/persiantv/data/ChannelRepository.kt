package ir.persiantv.data

import android.content.Context
import com.google.gson.Gson

object ChannelRepository {

    private var cachedChannels: List<Channel>? = null

    fun getChannels(context: Context): List<Channel> {
        cachedChannels?.let { return it }

        return try {
            val json = context.assets.open("channels.json").bufferedReader().use { it.readText() }
            val channelList = Gson().fromJson(json, ChannelList::class.java)
            cachedChannels = channelList.channels
            channelList.channels
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
