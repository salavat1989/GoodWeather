package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prod.goodweather.data.network.WeatherRepositoryImpl
import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.domain.usecase.GetCurrentWeatherUseCase
import com.prod.goodweather.utils.LiveCurrentLocation
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    val currentLocation: LiveCurrentLocation,
    private val getCurrentWeather: GetCurrentWeatherUseCase
    ):ViewModel() {

    val weather = MutableLiveData<CurrentWeatherDto>()
    init {
        loadCurrentWeather()
    }
    private fun loadCurrentWeather(){
        currentLocation.observeForever{
            viewModelScope.launch {
                val z = getCurrentWeather(it)
                weather.postValue(z)
            }
        }
    }
}
