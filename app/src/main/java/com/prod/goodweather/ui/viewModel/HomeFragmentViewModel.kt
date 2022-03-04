package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.ViewModel
import com.prod.goodweather.domain.usecase.GetCurrentLocationCurrentWeatherUseCase
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentLocationCurrentWeatherUseCase,
) : ViewModel() {

    val weather = getCurrentWeather()
}
