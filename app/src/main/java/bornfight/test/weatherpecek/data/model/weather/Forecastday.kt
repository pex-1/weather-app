package bornfight.test.weatherpecek.data.model.weather


import com.google.gson.annotations.SerializedName

data class Forecastday(
    @SerializedName("astro")
    val astro: Astro,
    @SerializedName("date")
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Double,
    @SerializedName("day")
    val day: Day
)