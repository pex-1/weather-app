package bornfight.test.weatherpecek.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.data.repository.Repository

class WeatherViewModel:ViewModel(), Observer<ApixuWeatherResponse> {

    var apiErrorLiveData: LiveData<String>? = null

    var showError: Boolean = true

    fun apiErrorSet(){
        apiErrorLiveData = Repository.apiErrorLiveData()
    }

    private var weatherForecast = MutableLiveData<ApixuWeatherResponse>()

    var youtubeSearch = ""

    val weatherForecastLiveData: LiveData<ApixuWeatherResponse>
    get(){
        return weatherForecast
    }

    override fun onChanged(t: ApixuWeatherResponse?) {
        weatherForecast.value = t
    }

    override fun onCleared() {
        Repository.weatherLiveData().removeObserver(this)
    }

    init{
        Repository.weatherLiveData().observeForever(this)
    }

    fun getWeatherForecast(searchQuery: String){
        Repository.getWeatherForecast(searchQuery)
    }



}