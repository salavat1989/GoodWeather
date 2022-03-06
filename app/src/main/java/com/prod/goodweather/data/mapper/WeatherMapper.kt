package com.prod.goodweather.data.mapper

import android.app.Application
import android.text.format.DateFormat
import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.model.LocalityAddressDto
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.domain.entity.HourlyWeather
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.entity.Weather
import java.text.SimpleDateFormat
import javax.inject.Inject

class WeatherMapper @Inject constructor(
    val application: Application,
) {
    fun mapWeatherDtoToEntity(dto: WeatherDto): Weather {
        val temperature = String.format("%.1f", dto.current.temp)
        var feelsLike = "- -"
        var weatherDescription: String? = null
        var iconUrl: String? = null
        val listHourlyWeather: MutableList<HourlyWeather> = mutableListOf()
        dto.current.let {
            it.feelsLike?.let { feelsLike = String.format("%.1f", it) }
            it.weather?.let {
                it.get(0)?.let {
                    weatherDescription = it.description?.replaceFirstChar {
                        it.uppercaseChar()
                    }
                    iconUrl = it.icon?.let { String.format(ApiFactory.IMAGE_URL_TEMPLATE, it) }
                }
            }
        }
        dto.hourly?.let {
            for (weather in it) {
                if(listHourlyWeather.size<24) {
                    listHourlyWeather.add(
                        mapHourlyWeatherDtoToEntity(weather)
                    )
                }
            }
        }
        return Weather(
            cityName = null,
            temperature = temperature,
            feelsLike = feelsLike,
            weatherDescription = weatherDescription,
            iconUrl = iconUrl,
            listHourlyWeather = listHourlyWeather
        )
    }

    fun mapHourlyWeatherDtoToEntity(dto: WeatherDto.Hourly): HourlyWeather {
        return HourlyWeather(
            convertUnixTimeToString(dto.dt),
            String.format("%.0f", dto.temp),
            dto.weather?.get(0)?.icon?.let { String.format(ApiFactory.IMAGE_URL_TEMPLATE, it) }
        )
    }

    private fun convertUnixTimeToString(t: Long): String {
//        val dateFormat = DateFormat.getTimeFormat(application).

        val template = "HH"
        val dateFormat = SimpleDateFormat(template)
        val date = java.util.Date(t * 1000)
        return dateFormat.format(date)
    }

    fun mapLocalityAddressToEntity(dto: LocalityAddressDto): LocalityAddress {
        val mainAddress = dto.locality ?: dto.subAdminArea ?: dto.adminArea ?: dto.countryName
        return LocalityAddress(mainAddress, dto.thoroughfare)
    }
}