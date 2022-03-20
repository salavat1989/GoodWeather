package com.prod.goodweather.data.mapper

import android.location.Address
import com.prod.goodweather.data.database.dbmodel.*
import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.model.WeatherDto
import com.prod.goodweather.domain.entity.*
import javax.inject.Inject

class WeatherMapper @Inject constructor(
	private val timeConverter: UnixTimeConverter,
) {
	fun mapWeatherDtoToEntity(dto: WeatherDto): Weather {
		val temperature = String.format("%.1f", dto.current.temp)
		val feelsLike = String.format("%.1f", dto.current.feelsLike)
		val weatherDescription = dto.current.weather[0].description.replaceFirstChar {
			it.uppercaseChar()
		}
		val iconUrl = String.format(ApiFactory.IMAGE_URL_TEMPLATE, dto.current.weather[0].icon)
		val listHourlyWeather: MutableList<HourlyWeather> = mutableListOf()
		val listDailyWeather: MutableList<DailyWeather> = mutableListOf()
		dto.hourly.let {
			for (weather in it) {
				if (listHourlyWeather.size < 24) {
					listHourlyWeather.add(
						mapHourlyWeatherDtoToEntity(weather, dto.timezone ?: "UTC")
					)
				}
			}
		}
		dto.daily.let {
			for (weather in it) {
				listDailyWeather.add(
					mapDailyWeatherDtoToEntity(weather, dto.timezone ?: "UTC")
				)
			}
		}
		return Weather(
			temperature = temperature,
			feelsLike = feelsLike,
			weatherDescription = weatherDescription,
			iconUrl = iconUrl,
			listHourlyWeather = listHourlyWeather,
			listDailyWeather = listDailyWeather,
		)
	}

	private fun mapHourlyWeatherDtoToEntity(
		dto: WeatherDto.Hourly,
		timeZone: String,
	): HourlyWeather {
		return HourlyWeather(
			unixTime = dto.dt,
			forecastTime = timeConverter.convertUnixTimeToHour(dto.dt, timeZone),
			temperature = String.format("%.0f", dto.temp),
			iconURL = dto.weather[0].icon.run {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					this)
			}
		)
	}

	private fun mapDailyWeatherDtoToEntity(dto: WeatherDto.Daily, timeZone: String): DailyWeather {
		return DailyWeather(
			unixTime = dto.dt,
			forecastTime = timeConverter.convertUnixTimeToDay(dto.dt, timeZone),
			temperatureMin = String.format("%.0f", dto.temp.min),
			temperatureMax = String.format("%.0f", dto.temp.max),
			iconURL = dto.weather[0].icon.run {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					this)
			}
		)
	}

	fun mapAddressToAddressModel(address: Address): AddressModel {
		val mainAddress =
			address.locality ?: address.subAdminArea ?: address.adminArea ?: address.countryName
			?: ""
		val addressLine =
			"${address.thoroughfare ?: ""} ${address.locality ?: ""} ${address.adminArea ?: ""} ${address.countryName ?: ""}".trim()
		val location = LocationModel(address.latitude, address.longitude)
		return AddressModel(mainAddress, address.thoroughfare, addressLine, location)
	}

	fun mapAddressModelDBToAddressModel(db: AddressModelDb): AddressModel {
		return AddressModel(
			db.mainAddress,
			db.subAddress,
			"",
			LocationModel(db.latitude, db.longitude)
		)
	}

	fun mapWeatherDtoToAllDb(dto: WeatherDto, locationID: Long): AllWeatherDb {
		val temperature = String.format("%.1f", dto.current.temp)
		val feelsLike = String.format("%.1f", dto.current.feelsLike)
		val weatherDescription = dto.current.weather[0].description.replaceFirstChar {
			it.uppercaseChar()
		}
		val iconUrl = String.format(ApiFactory.IMAGE_URL_TEMPLATE, dto.current.weather[0].icon)
		val listHourlyWeather: MutableList<HourlyWeatherDb> = mutableListOf()
		val listDailyWeather: MutableList<DailyWeatherDb> = mutableListOf()
		dto.hourly.let {
			for (weather in it) {
				if (listHourlyWeather.size < 24) {
					listHourlyWeather.add(
						mapHourlyWeatherDtoToDb(weather)
					)
				}
			}
		}
		dto.daily.let {
			for (weather in it) {
				listDailyWeather.add(
					mapDailyWeatherDtoToDb(weather)
				)
			}
		}
		val weatherDb = WeatherDb(
			id = 0,
			temperature = temperature,
			feelsLike = feelsLike,
			weatherDescription = weatherDescription,
			iconUrl = iconUrl,
			timezone = dto.timezone ?: "UTC",
			addressModelId = locationID
		)

		return AllWeatherDb(
			weatherDb,
			listHourlyWeather,
			listDailyWeather
		)
	}

	private fun mapHourlyWeatherDtoToDb(dto: WeatherDto.Hourly): HourlyWeatherDb {
		return HourlyWeatherDb(
			id = 0,
			unixTime = dto.dt,
			temperature = String.format("%.0f", dto.temp),
			iconURL = dto.weather[0].icon.run {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					this)
			},
			weatherId = 0
		)
	}

	private fun mapDailyWeatherDtoToDb(dto: WeatherDto.Daily): DailyWeatherDb {
		return DailyWeatherDb(
			id = 0,
			unixTime = dto.dt,
			temperatureMin = String.format("%.0f", dto.temp.min),
			temperatureMax = String.format("%.0f", dto.temp.max),
			iconURL = dto.weather[0].icon.run {
				String.format(ApiFactory.IMAGE_URL_TEMPLATE,
					this)
			},
			weatherId = 0
		)
	}

	fun mapAllWeatherDbToEntity(db: AllWeatherDb): Weather {
		val hourlyWeather =
			mapListHourlyWeatherDbToEntity(db.listHourlyWeatherDb, db.weatherDb.timezone)
		val dailyWeather =
			mapListDailyWeatherDbToEntity(db.listDailyWeatherDb, db.weatherDb.timezone)
		return Weather(
			db.weatherDb.temperature,
			db.weatherDb.feelsLike,
			db.weatherDb.weatherDescription,
			db.weatherDb.iconUrl,
			hourlyWeather,
			dailyWeather
		)
	}

	private fun mapHourlyWeatherDbToEntity(db: HourlyWeatherDb, timeZone: String): HourlyWeather {
		return HourlyWeather(
			db.unixTime,
			timeConverter.convertUnixTimeToHour(db.unixTime, timeZone),
			db.temperature,
			db.iconURL
		)
	}

	private fun mapListHourlyWeatherDbToEntity(
		listDb: List<HourlyWeatherDb>,
		timeZone: String,
	): List<HourlyWeather> = listDb.map { mapHourlyWeatherDbToEntity(it, timeZone) }

	private fun mapDailyWeatherDbToEntity(db: DailyWeatherDb, timeZone: String): DailyWeather {
		return DailyWeather(
			db.unixTime,
			timeConverter.convertUnixTimeToDay(db.unixTime, timeZone),
			db.temperatureMin,
			db.temperatureMax,
			db.iconURL
		)
	}

	private fun mapListDailyWeatherDbToEntity(
		listDb: List<DailyWeatherDb>,
		timeZone: String,
	): List<DailyWeather> = listDb.map { mapDailyWeatherDbToEntity(it, timeZone) }
}