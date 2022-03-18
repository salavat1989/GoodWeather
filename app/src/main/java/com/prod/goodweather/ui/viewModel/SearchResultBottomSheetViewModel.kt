package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.Weather
import com.prod.goodweather.domain.usecase.GetLocationWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchResultBottomSheetViewModel @Inject constructor(
	private val addressModel: AddressModel?,
	private val getWeather: GetLocationWeatherUseCase,
) : ViewModel() {
	init {
		viewModelScope.launch {
			addressModel?.let {
				val weather = getWeather(it.location)
				_weather.postValue(weather)
			}

		}
	}

	private val _weather = MutableLiveData<Weather>()
	val weather: LiveData<Weather>
		get() = _weather


}
