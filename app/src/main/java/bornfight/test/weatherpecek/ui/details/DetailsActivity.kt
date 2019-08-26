package bornfight.test.weatherpecek.ui.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import bornfight.test.weatherpecek.BuildConfig
import bornfight.test.weatherpecek.R
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment


class DetailsActivity : AppCompatActivity() {

    companion object {
        const val DETAILS = "DETAILS"
        fun newInstance(context: Context, searchQuery: String): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DETAILS, searchQuery)
            return intent
        }
    }

    private lateinit var viewModel: DetailsViewModel
    private var youtube: YouTubePlayer? = null

    private lateinit var onInitializedListener: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        if (viewModel.youtubeSeek == 0) {
            viewModel.getYoutubeVideos(intent.getStringExtra(DETAILS))
        }
        viewModel.youtubeIdLiveData?.observe(this, Observer {

            initYoutube(it)
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.youtubeSeek = youtube?.currentTimeMillis ?: 0
    }

    private fun initYoutube(videoId: String) {
        onInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(youtubeProvider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, p2: Boolean) {
                youtube = youTubePlayer
                youTubePlayer?.loadVideo(videoId, viewModel.youtubeSeek)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        }
        val fragment =
            supportFragmentManager.findFragmentById(R.id.youtubePlayerFragment) as YouTubePlayerSupportFragment
        fragment.initialize(BuildConfig.ApiKeyYoutube, onInitializedListener)
    }
}
