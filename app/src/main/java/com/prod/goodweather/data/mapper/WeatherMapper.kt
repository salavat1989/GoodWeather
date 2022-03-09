package com.prod.goodweather.data.mapper

import android.app.Application
import android.location.Address
import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.domain.entity.*
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
		val listDailyWeather: MutableList<DailyWeather> = mutableListOf()
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
		dto.daily?.let {
			for (weather in it) {
				listDailyWeather.add(
					mapDailyWeatherDtoToEntity(weather, dto.timezone ?: "UTC")
				)
			}
		}
		return Weather(
			cityName = null,
			temperature = temperature,
			feelsLike = feelsLike,
			weatherDescription = weatherDescription,
			iconUrl = iconUrl,
			listHourlyWeather = listHourlyWeather,
			listDailyWeather = listDailyWeather,
		)
	}

	fun mapHourlyWeatherDtoToEntity(dto: WeatherDto.Hourly, timeZone: String): HourlyWeather {
		return HourlyWeather(
			unixTime = dto.dt,
			forecastTime = convertUnixTimeToString(dto.dt, timeZone, "HH"),
			temperature = String.format("%.0f", dto.temp),
			iconURL = dto.weather?.get(0)?.icon?.let {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					it)
			}
		)
	}

	fun mapDailyWeatherDtoToEntity(dto: WeatherDto.Daily, timeZone: String): DailyWeather {
		return DailyWeather(
			unixTime = dto.dt,
			forecastTime = convertUnixTimeToString(dto.dt,
				timeZone,
				"EEEE").replaceFirstChar { it.uppercaseChar() },
			temperatureMin = String.format("%.0f", dto.temp.min),
			temperatureMax = String.format("%.0f", dto.temp.max),
			iconURL = dto.weather?.get(0)?.icon?.let {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					it)
			}
		)
	}

	private fun convertUnixTimeToString(t: Long, timeZone: String, template: String): String {
		val dateFormat = SimpleDateFormat(template)
		val date = Date(t * 1000)
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone))
		} catch (e: Exception) {
		}
		return dateFormat.format(date)
	}

	//    fun mapLocalityAddressToEntity(dto: LocalityAddressDto): AddressModel {
//        val mainAddress = dto.locality ?: dto.subAdminArea ?: dto.adminArea ?: dto.countryName
//        return AddressModel(mainAddress, dto.thoroughfare)
//    }
	fun mapAddressToAddressModel(address: Address): AddressModel {
		val mainAddress =
			address.locality ?: address.subAdminArea ?: address.adminArea ?: address.countryName
			?: ""
		val addressLine =
			"${address.thoroughfare ?: ""} ${address.locality ?: ""} ${address.adminArea ?: ""} ${address.countryName ?: ""}".trim()
		val location = LocationModel(address.latitude, address.longitude)
		return AddressModel(mainAddress, address.thoroughfare, addressLine, location)
	}
}