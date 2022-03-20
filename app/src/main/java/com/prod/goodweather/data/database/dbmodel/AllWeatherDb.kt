package com.prod.goodweather.data.database.dbmodel

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Kadyrov Salavat on 19.03.2022
 */

data class AllWeatherDb(
	@Embedded
	val weatherDb: WeatherDb,
	@Relation(
		parentColumn = "id",
		entity = HourlyWeatherDb::class,
		entityColumn = "weather_id"
	)
	val listHourlyWeatherDb: List<HourlyWeatherDb>,
	@Relation(
		parentColumn = "id",
		entity = DailyWeatherDb::class,
		entityColumn = "weather_id"
	)
	val listDailyWeatherDb: List<DailyWeatherDb>,
)