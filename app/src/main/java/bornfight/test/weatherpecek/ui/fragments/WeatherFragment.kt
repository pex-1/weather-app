package bornfight.test.weatherpecek.ui.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import bornfight.test.weatherpecek.Constants
import bornfight.test.weatherpecek.R
import bornfight.test.weatherpecek.Utils
import bornfight.test.weatherpecek.data.model.weather.ApixuWeatherResponse
import bornfight.test.weatherpecek.ui.weather.WeatherAdapter
import bornfight.test.weatherpecek.ui.weather.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var adapter: WeatherAdapter
    private var navController: NavController? = null


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.isIconified = true
        searchView.queryHint = Constants.SEARCH


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean = false

            override fun onQueryTextSubmit(query: String): Boolean {
                if (Utils.networkAvailable(requireContext())) {
                    viewModel.showError = true
                    searchView.isIconified = true
                    viewModel.getWeatherForecast(query)

                    cardViewProgressBar.show()
                } else {
                    createSnackbar(Constants.NO_NETWORK)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        setToolbar()

        adapter = WeatherAdapter()

        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.apiErrorSet()

        viewModel.apiErrorLiveData?.observe(this, Observer {
            if(viewModel.showError){
                cardViewProgressBar.hide()
                createSnackbar(it)
                viewModel.showError = false
            }
        })

        viewModel.weatherForecastLiveData.observe(this, Observer {
            cardViewProgressBar.hide()
            emptyScreenFrameLayout.visibility = View.GONE

            if (it.location.name.isNotEmpty()) {
                viewModel.youtubeSearch = "${it.current.condition.text} ${it.location.region}"

                setUI(it)

                adapter.setData(it.forecast.forecastday.drop(1))
                daysRecyclerView.layoutManager = LinearLayoutManager(activity)
                daysRecyclerView.adapter = adapter
            }
        })

        todayConstraintLayout.setOnClickListener {
            val bundle = bundleOf(Constants.VIDEO_QUERY to viewModel.youtubeSearch)
            navController?.navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        }

    }

    private fun setToolbar() {
        with((requireActivity() as AppCompatActivity)) {
            setSupportActionBar(toolbar)
            setHasOptionsMenu(true)
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
    }

    private fun createSnackbar(message: String) {
        val snackbar = Snackbar.make(mainFragmentConstraintLayout, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
        snackbar.show()
    }

    private fun CardView.hide() {
        visibility = View.GONE
    }

    fun CardView.show() {
        visibility = View.VISIBLE
    }

}
