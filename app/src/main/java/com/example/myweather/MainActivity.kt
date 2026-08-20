package com.example.myweather

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.SearchView
import com.example.myweather.MainViewModel.WeatherUiState
import com.example.myweather.databinding.ActivityMainBinding
import com.example.myweather.utils.Utils

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.mainContent.setPaddingRelative(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        viewModel.fetchWeatherData(
            apiKey = BuildConfig.API_KEY,
            units = "metric"
        )
        viewModel.weatherData.observe(this) { uiState ->
            when (uiState) {
                is WeatherUiState.Success -> {
                    val conditions = uiState.weatherData.weather.firstOrNull()?.main
                        ?: getString(R.string.unknown)
                    binding.today.text =
                        getString(R.string.temp_format, uiState.weatherData.main.temp)
                    binding.cityText.text = uiState.weatherData.name
                    binding.weatherTextView.text =
                        uiState.weatherData.weather.firstOrNull()?.main
                            ?: getString(R.string.unknown)
                    binding.maxTempView.text =
                        getString(R.string.temp_format, uiState.weatherData.main.temp_max)
                    binding.minTempView.text =
                        getString(R.string.temp_format, uiState.weatherData.main.temp_min)
                    binding.dayTextView.text = Utils.dayName()
                    binding.dateTextView.text = Utils.date()
                    binding.humidity.text =
                        getString(R.string.humidity_format, uiState.weatherData.main.humidity)
                    binding.wind.text =
                        getString(R.string.wind_speed_format, uiState.weatherData.wind.speed)
                    binding.condition.text = conditions
                    binding.sunset.text = Utils.time(uiState.weatherData.sys.sunset.toLong())
                    binding.sunrise.text = Utils.time(uiState.weatherData.sys.sunrise.toLong())
                    binding.seaLevel.text =
                        getString(R.string.sea_level_format, uiState.weatherData.main.sea_level)
                    changeImagesAccordingToWeatherCondition(conditions)
                    binding.loadingView.visibility = View.GONE
                }

                is WeatherUiState.Error -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.loadingText.text = uiState.message
                    binding.loadingBar.visibility = View.GONE
                    binding.button.visibility = View.VISIBLE
                }

                WeatherUiState.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                    binding.button.visibility = View.GONE
                    binding.loadingText.text = getString(R.string.loading_message)
                    binding.loadingBar.visibility = View.VISIBLE
                }
            }
        }
        searchCity()
        binding.button.setOnClickListener {
            viewModel.fetchWeatherData(
                apiKey = BuildConfig.API_KEY,
                units = "metric"
            )
        }


    }

    private fun searchCity() {
        val searchView = binding.searchBar
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchWeatherData(
                        city = it,
                        apiKey = BuildConfig.API_KEY,
                        units = "metric"
                    )
                }
                return true
            }
        })

    }

    private fun changeImagesAccordingToWeatherCondition(conditions: String) {
        when (conditions) {
               "Clear Sky", "Sunny", "Clear" -> {
                binding.main.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }

            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy", "Haze" -> {
                binding.main.setBackgroundResource(R.drawable.cloud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain", "Rain" -> {
                binding.main.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
                binding.main.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }

            else -> {
                binding.main.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }

        }
        binding.lottieAnimationView.playAnimation()
    }


}

