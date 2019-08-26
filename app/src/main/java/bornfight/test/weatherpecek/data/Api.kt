package bornfight.test.weatherpecek.data

import bornfight.test.weatherpecek.BuildConfig
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.data.model.youtube.YoutubeResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface Api {

    @GET("search?key=${BuildConfig.ApiKeyYoutube}&part=snippet&type=video&maxResults=1")
    fun getYoutubeSearchResults(@Query("q") query: String): Call<YoutubeResponse>

    @GET("forecast.json?key=${BuildConfig.ApiKeyApixuWeather}")
    fun getWeatherForecast2(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en"
    ): Call<ApixuWeatherResponse>

}