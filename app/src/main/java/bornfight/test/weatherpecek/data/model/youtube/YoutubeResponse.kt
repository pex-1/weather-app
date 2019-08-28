package bornfight.test.weatherpecek.data.model.youtube


import com.google.gson.annotations.SerializedName

data class YoutubeResponse(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String,
    @SerializedName("regionCode")
    val regionCode: String
)