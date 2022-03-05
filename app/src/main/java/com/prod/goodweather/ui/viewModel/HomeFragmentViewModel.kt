package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.ViewModel
import com.prod.goodweather.domain.usecase.GetAddressUseCase
import com.prod.goodweather.domain.usecase.GetCurrentLocationCurrentWeatherUseCase
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    private val getCurrentWeather: GetCurrentLocationCurrentWeatherUseCase,
    private val getAddressUseCase: GetAddressUseCase,
) : ViewModel() {
    val address = getAddressUseCase()
    val weather = getCurrentWeather()
}
