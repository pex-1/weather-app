package bornfight.test.weatherpecek.data.model.weather


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avghumidity")
    val avghumidity: Double,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    @SerializedName("avgtemp_f")
    val avgtempF: Double,
    @SerializedName("avgvis_km")
    val avgvisKm: Double,
    @SerializedName("avgvis_miles")
    val avgvisMiles: Double,
    @SerializedName("condition")
    val condition: Condition
)