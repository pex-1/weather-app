package bornfight.test.weatherpecek.data.model.weather


import com.google.gson.annotations.SerializedName

data class ConditionX(
    @SerializedName("code")
    val code: Double,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)