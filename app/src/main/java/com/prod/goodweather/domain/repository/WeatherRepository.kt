package com.prod.goodweather.domain.repository

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.CurrentWeather
import com.prod.goodweather.domain.entity.LocalityAddress

interface WeatherRepository {

    fun getCurrentLocationCurrentWeather(): LiveData<CurrentWeather>

    fun getCurrentLocationAddress(): LiveData<LocalityAddress>
}