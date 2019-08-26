package bornfight.test.weatherpecek.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bornfight.test.weatherpecek.R
import bornfight.test.weatherpecek.Utils
import bornfight.test.weatherpecek.data.model.weather.Forecastday
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {


    private var weather = listOf<Forecastday>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false))
    }

    override fun getItemCount(): Int = weather.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val day = weather[position]

        with(holder.itemView){
            itemDayTextView.text = Utils.getDayOfTheWeek(day.date)
            itemDegreesTextView.text =
                Utils.getDegrees(day.day.avgtempC)
            Utils.picassoUpload(day.day.condition.icon, itemImageView)

        }
    }

    fun setData(weather: List<Forecastday>){
        this.weather = weather
        notifyDataSetChanged()
    }


    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}