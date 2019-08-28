package bornfight.test.weatherpecek.ui.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import bornfight.test.weatherpecek.Constants
import bornfight.test.weatherpecek.R
import bornfight.test.weatherpecek.Utils
import bornfight.test.weatherpecek.ui.details.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker


class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private var navController: NavController? = null
    private val tracker = YouTubePlayerTracker()
    private lateinit var searchQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        searchQuery = arguments!!.getString(Constants.VIDEO_QUERY) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity!!.onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }



    override fun onPause() {
        super.onPause()
        viewModel.youtubeSeek = tracker.currentSecond
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setToolbar()

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        if(Utils.networkAvailable(requireContext())){
            viewModel.getYoutubeVideos(searchQuery)
            viewModel.youtubeIdLiveData?.observe(this, Observer {link ->

                initYoutube(link)
            })
        }


    }

    private fun setToolbar() {
        with((requireActivity() as AppCompatActivity)) {
            setSupportActionBar(toolbarDetails)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initYoutube(link: String) {
        lifecycle.addObserver(youtube_player_view)
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                if(resources.getBoolean(R.bool.landscape)){
                    youtube_player_view.enterFullScreen()
                }

                youTubePlayer.addListener(tracker)
                youTubePlayer.loadVideo(link, viewModel.youtubeSeek)
                youTubePlayer.play()
            }
        })
    }
}



