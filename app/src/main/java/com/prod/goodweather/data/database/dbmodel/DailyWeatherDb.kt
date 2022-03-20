package com.prod.goodweather.data.database.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "daily_weather",
	foreignKeys = [
		ForeignKey(
			entity = WeatherDb::class,
			parentColumns = ["id"],
			childColumns = ["weather_id"],
			onDelete = ForeignKey.CASCADE
		)])
data class DailyWeatherDb(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val unixTime: Long,
	val temperatureMin: String,
	val temperatureMax: String,
	val iconURL: String,
	@ColumnInfo(name = "weather_id")
	var weatherId: Long,
)