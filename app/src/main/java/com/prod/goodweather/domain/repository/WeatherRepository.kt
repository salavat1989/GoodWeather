package com.prod.goodweather.domain.repository

import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.entity.Weather

interface WeatherRepository {
    suspend fun getLocationAddress(location: LocationModel): AddressModel

    suspend fun getLocationWeather(location: LocationModel): Weather



    suspend fun getLocationsFromSubString(subString: String): List<AddressModel>

}