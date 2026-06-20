package ir.persiantv.data

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("logo") val logo: String = "",
    @SerializedName("category") val category: String = ""
)

data class ChannelList(
    @SerializedName("channels") val channels: List<Channel>
)
