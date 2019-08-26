package bornfight.test.weatherpecek.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bornfight.test.weatherpecek.data.Api
import bornfight.test.weatherpecek.data.RetrofitClient
import bornfight.test.weatherpecek.data.RetrofitClientApixu
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.data.model.youtube.YoutubeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private const val DAYS = 7
    private var apiService = RetrofitClient.retrofitInstance?.create(Api::class.java)

    private var apiServiceApixu = RetrofitClientApixu.retrofitInstance?.create(Api::class.java)

    private val youtubeVideoId = MutableLiveData<String>()
    fun getYoutubeId(): LiveData<String> = youtubeVideoId

    private val weatherForecast = MutableLiveData<ApixuWeatherResponse>()
    fun weatherLiveData(): LiveData<ApixuWeatherResponse> = weatherForecast


    fun getYoutubeVideos(search: String){
        apiService?.getYoutubeSearchResults(search)?.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                youtubeVideoId.postValue(response.body()?.items?.get(0)?.id?.videoId)

            }
            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
            }

        })
    }


    fun getWeatherForecast(searchQuery: String){
        apiServiceApixu?.getWeatherForecast2(searchQuery, DAYS)?.enqueue(object : Callback<ApixuWeatherResponse>{
            override fun onResponse(call: Call<ApixuWeatherResponse>, response: Response<ApixuWeatherResponse>) {
                weatherForecast.value = response.body()
            }
            override fun onFailure(call: Call<ApixuWeatherResponse>, t: Throwable) {
            }

        })
    }
}