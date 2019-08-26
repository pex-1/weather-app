package bornfight.test.weatherpecek.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import bornfight.test.weatherpecek.data.repository.Repository

class DetailsViewModel:ViewModel() {

    var youtubeSeek = 0

    var youtubeIdLiveData: LiveData<String>? = null


    fun getYoutubeVideos(search: String){
        Repository.getYoutubeVideos(search)
        youtubeIdLiveData = Repository.getYoutubeId()
    }

}