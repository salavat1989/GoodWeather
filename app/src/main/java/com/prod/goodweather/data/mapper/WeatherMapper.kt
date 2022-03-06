package com.prod.goodweather.data.mapper

import android.app.Application
import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.model.LocalityAddressDto
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.domain.entity.HourlyWeather
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.entity.Weather
import java.text.SimpleDateFormat
import java.util.*
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
                if (listHourlyWeather.size < 24) {
                    listHourlyWeather.add(
                        mapHourlyWeatherDtoToEntity(weather, dto.timezone ?: "UTC")
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

    fun mapHourlyWeatherDtoToEntity(dto: WeatherDto.Hourly, timeZone: String): HourlyWeather {
        return HourlyWeather(
            convertUnixTimeToString(dto.dt, timeZone),
            String.format("%.0f", dto.temp),
            dto.weather?.get(0)?.icon?.let { String.format(ApiFactory.IMAGE_URL_TEMPLATE, it) }
        )
    }

    private fun convertUnixTimeToString(t: Long, timeZone: String): String {
//        val dateFormat = DateFormat.getTimeFormat(application).

        val template = "HH"
        val dateFormat = SimpleDateFormat(template)
        val date = Date(t * 1000)
        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone))
        } catch (e: Exception) {
        }
        return dateFormat.format(date)
    }

    fun mapLocalityAddressToEntity(dto: LocalityAddressDto): LocalityAddress {
        val mainAddress = dto.locality ?: dto.subAdminArea ?: dto.adminArea ?: dto.countryName
        return LocalityAddress(mainAddress, dto.thoroughfare)
    }
}