package bornfight.test.weatherpecek.data.model.weather


import com.google.gson.annotations.SerializedName

data class ApixuWeatherResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location


)