package com.prod.goodweather.data.mapper

import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.model.LocalityAddressDto
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.entity.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    fun mapWeatherDtoToEntity(dto: WeatherDto): Weather {
        var temperature = "- -"
        var feelsLike = "- -"
        var weatherDescription: String? = null
        var iconUrl: String? = null
        dto.current?.let {
            it.temp?.let { temperature = String.format("%.1f", it) }
            it.feelsLike?.let { feelsLike = String.format("%.1f", it) }
            it.weather?.let {
                it.get(0)?.let {
                    weatherDescription = it.description?.replaceFirstChar{
                        it.uppercaseChar()
                    }
                    iconUrl = it.icon?.let { String.format(ApiFactory.IMAGE_URL_TEMPLATE, it) }
                }
            }
        }
        return Weather(
            cityName = null,
            temperature = temperature,
            feelsLike = feelsLike,
            weatherDescription = weatherDescription,
            iconUrl = iconUrl
        )
    }

    fun mapLocalityAddressToEntity(dto: LocalityAddressDto): LocalityAddress {
        val mainAddress = dto.locality ?: dto.subAdminArea ?: dto.adminArea ?: dto.countryName
        return LocalityAddress(mainAddress, dto.thoroughfare)
    }
}