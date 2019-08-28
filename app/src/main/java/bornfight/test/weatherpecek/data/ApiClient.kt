package bornfight.test.weatherpecek.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL_YOUTUBE = "https://www.googleapis.com/youtube/v3/"
    private const val BASE_URL_APIXU = "https://api.apixu.com/v1/"

    private var retrofit: Retrofit.Builder? = null

    val apiYoutube: ApiYouTube?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
            }
            return retrofit?.baseUrl(BASE_URL_YOUTUBE)?.build()?.create(ApiYouTube::class.java)
        }

    val apixu: ApiApixu?
    get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
        }
        return retrofit?.baseUrl(BASE_URL_APIXU)?.build()?.create(ApiApixu::class.java)
    }

}