package bornfight.test.weatherpecek.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import bornfight.test.weatherpecek.R
import bornfight.test.weatherpecek.Utils
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        setSupportActionBar(toolbar)

        adapter = WeatherAdapter()

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        viewModel.weatherForecastLiveData.observe(this, Observer {
            cardViewProgressBar.visibility = View.GONE
            emptyScreenFrameLayout.visibility = View.GONE

            if(it!=null){
                viewModel.youtubeSearch = "${it.current.condition.text} ${it.location.region}"
                setUI(it)
            }
        })

        todayConstraintLayout.setOnClickListener{
            startActivity(DetailsActivity.newInstance(this, viewModel.youtubeSearch))
        }
    }

    private fun setUI(it: ApixuWeatherResponse) {
        val today = it.forecast.forecastday[0]

        toolbar.title = it.location.name
        dayTextView.text = Utils.getDayOfTheWeek(today.date)
        degreesTextView.text = Utils.getDegrees(today.day.avgtempC)
        Utils.picassoUpload(today.day.condition.icon, forecastImageView)
        dateTextView.text = Utils.getDate(today.date)
        conditionTextView.text = today.day.condition.text

        adapter.setData(it.forecast.forecastday.drop(1))
        daysRecyclerView.layoutManager = LinearLayoutManager(this)
        daysRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if(Utils.networkAvailable(applicationContext)){
                    searchView.isIconified = true
                    searchView.clearFocus()
                    viewModel.getWeatherForecast(query)
                    cardViewProgressBar.visibility = View.VISIBLE
                }else{
                    Toast.makeText(applicationContext,"No network connection!",Toast.LENGTH_SHORT).show()
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
