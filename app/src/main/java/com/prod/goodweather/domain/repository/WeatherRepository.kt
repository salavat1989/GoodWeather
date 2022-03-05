package com.prod.goodweather.domain.repository

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.entity.Weather

interface WeatherRepository {

    fun getCurrentLocationWeather(): LiveData<Weather>

    fun getCurrentLocationAddress(): LiveData<LocalityAddress>
}