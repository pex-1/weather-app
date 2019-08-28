package bornfight.test.weatherpecek.data

import bornfight.test.weatherpecek.BuildConfig
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface ApiApixu {

    @GET("forecast.json?key=${BuildConfig.ApiKeyApixuWeather}")
    fun getWeatherForecast2(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en"
    ): Call<ApixuWeatherResponse>

}