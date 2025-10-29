package com.example.myweather

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.fetchWeatherData(
            apiKey = BuildConfig.API_KEY,
            units = "metric"
        )
        viewModel.weatherData.observe(this) { uiState ->
            when (uiState) {
                is WeatherUiState.Success -> {
                    val conditions = uiState.weatherData.weather.firstOrNull()?.main ?: "Unknown"
                    binding.today.text = "${uiState.weatherData.main.temp.toString()} C°"
                    binding.cityText.text = uiState.weatherData.name
                    binding.weatherTextView.text =
                        uiState.weatherData.weather.firstOrNull()?.main ?: "Unknown"
                    binding.maxTempView.text = "${uiState.weatherData.main.temp_max.toString()} C°"
                    binding.minTempView.text =  "${uiState.weatherData.main.temp_min.toString()} C°"
                    binding.dayTextView.text = Utils.dayName()
                    binding.dateTextView.text = Utils.date()
                    binding.humidity.text = "${uiState.weatherData.main.humidity.toString()} %"
                    binding.wind.text = uiState.weatherData.wind.speed.toString()
                    binding.condition.text = conditions
                    binding.sunset.text = Utils.time(uiState.weatherData.sys.sunset.toLong())
                    binding.sunrise.text = Utils.time(uiState.weatherData.sys.sunrise.toLong())
                    binding.seaLevel.text = uiState.weatherData.main.sea_level.toString()
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
                    binding.loadingText.text = "Please! wait Loading"+"😘"
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
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
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

            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.main.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain", "Rain" -> {
                binding.main.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
                binding.main.setBackgroundResource(R.drawable.snow_background)
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

