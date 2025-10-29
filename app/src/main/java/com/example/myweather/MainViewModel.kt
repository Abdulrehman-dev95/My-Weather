package com.example.myweather

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myweather.data.NetworkRepository
import com.example.myweather.model.WeatherData

class MainViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    sealed class WeatherUiState {
        data class Success(val weatherData: WeatherData) : WeatherUiState()
        data class Error(val message: String) : WeatherUiState()
        object Loading : WeatherUiState()
    }


    private val _weatherData = MutableLiveData<WeatherUiState>()
    val weatherData: LiveData<WeatherUiState> = _weatherData


    fun fetchWeatherData(city: String = "mian channu", apiKey: String, units: String) {
        _weatherData.value = WeatherUiState.Loading
        networkRepository.getWeatherData(city, apiKey, units) { result ->
            result.onSuccess {
                _weatherData.value = WeatherUiState.Success(it)
            }
            result.onFailure {
                _weatherData.value = WeatherUiState.Error(it.message ?: "Unknown error")
            }

        }


    }


    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                MainViewModel(
                    application.appContainer.networkRepository
                )

            }

        }


    }


}