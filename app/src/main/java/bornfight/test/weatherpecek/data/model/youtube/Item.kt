package bornfight.test.weatherpecek.data.model.youtube


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: Id,
    @SerializedName("kind")
    val kind: String
)