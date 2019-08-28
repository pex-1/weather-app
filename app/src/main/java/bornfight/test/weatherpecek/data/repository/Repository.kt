package bornfight.test.weatherpecek.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bornfight.test.weatherpecek.data.ApiClient
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.data.model.youtube.YoutubeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
    private const val DAYS = 7
    private var apiService = ApiClient.apiYoutube

    private var apiServiceApixu = ApiClient.apixu

    private val youtubeVideoId = MutableLiveData<String>()
    fun getYoutubeId(): LiveData<String> = youtubeVideoId

    private val weatherForecast = MutableLiveData<ApixuWeatherResponse>()
    fun weatherLiveData(): LiveData<ApixuWeatherResponse> = weatherForecast

    private val apiError = MutableLiveData<String>()
    fun apiErrorLiveData():LiveData<String> = apiError



    fun getYoutubeVideos(search: String) {
        apiService?.getYoutubeSearchResults(search)?.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(call: Call<YoutubeResponse>, response: Response<YoutubeResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    youtubeVideoId.postValue(response.body()?.items?.get(0)?.id?.videoId)
                }
            }
            override fun onFailure(call: Call<YoutubeResponse>, t: Throwable) {
            }
        })
    }


    fun getWeatherForecast(searchQuery: String) {
        apiServiceApixu?.getWeatherForecast2(searchQuery, DAYS)
            ?.enqueue(object : Callback<ApixuWeatherResponse> {
                override fun onResponse(call: Call<ApixuWeatherResponse>, response: Response<ApixuWeatherResponse>) {
                    if(response.isSuccessful && response.body()!= null){
                        weatherForecast.value = response.body()
                    }else{
                        apiError.postValue(response.errorBody()?.string()?: DEFAULT_ERROR_MESSAGE)
                    }
                }

                override fun onFailure(call: Call<ApixuWeatherResponse>, t: Throwable) {
                    apiError.postValue(t.message?: DEFAULT_ERROR_MESSAGE)
                }

            })
    }
}