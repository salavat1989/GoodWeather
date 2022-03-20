package com.prod.goodweather.data.database.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_weather",
	foreignKeys = [
		androidx.room.ForeignKey(
			entity = WeatherDb::class,
			parentColumns = ["id"],
			childColumns = ["weather_id"],
			onDelete = ForeignKey.CASCADE
		)])
data class HourlyWeatherDb(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val unixTime: Long,
	val temperature: String,
	val iconURL: String,
	@ColumnInfo(name = "weather_id")
	var weatherId: Long,
)