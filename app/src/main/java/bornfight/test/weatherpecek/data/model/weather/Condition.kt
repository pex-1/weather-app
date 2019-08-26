package bornfight.test.weatherpecek.data.model.weather


import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("code")
    val code: Double,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)