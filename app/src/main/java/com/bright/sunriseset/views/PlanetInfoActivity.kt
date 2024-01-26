package com.bright.sunriseset.views

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bright.sunriseset.R
import com.bright.sunriseset.databinding.ActivityMainBinding
import com.bright.sunriseset.utils.ContextUtils
import com.bright.sunriseset.viewmodels.PlanetInfoViewModel
import kotlinx.coroutines.launch
import java.util.Locale


class PlanetInfoActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PlanetInfoViewModel

     // attach new updated context with new locale
    override fun attachBaseContext(newBase: Context) {
        val localeToSwitch = Locale("zh") //zh is the language code for Chinese
        val localeUpdatedContext = ContextUtils.updateLocale(newBase, localeToSwitch)
        super.attachBaseContext(localeUpdatedContext)
    }

    /**
     * Called when the activity is first created. This function initializes the activity, inflates the layout,
     * retrieves the current time, and asynchronously fetches sunrise and sunset times from an API.
     * The fetched times are then dynamically localized and displayed in Chinese on TextViews.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(PlanetInfoViewModel::class.java)

        // Observe changes of sunrise
        viewModel.sunriseLiveData.observe(this) {
            binding.textViewSunrise.text = String.format(getString(R.string.SunriseTime), it)
        }

        // Observe changes of sunset
        viewModel.sunsetLiveData.observe(this) {
            binding.textViewSunset.text = String.format(getString(R.string.SunsetTime), it)
        }

        // Start fetching sunset and sunrise
        launch {
            viewModel.fetchSunriseAndSet()
        }
    }
}