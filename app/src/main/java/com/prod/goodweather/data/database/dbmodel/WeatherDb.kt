package com.prod.goodweather.data.database.dbmodel

import androidx.room.*

@Entity(tableName = "weather",
	foreignKeys = [
		ForeignKey(
			entity = AddressModelDb::class,
			parentColumns = ["id"],
			childColumns = ["address_id"],
			onDelete = ForeignKey.CASCADE
		)],
	indices = [
		Index(value = ["id"], unique = true)]
)
data class WeatherDb(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	val timezone: String,
	val temperature: String,
	val feelsLike: String,
	val weatherDescription: String,
	val iconUrl: String,
	@ColumnInfo(name = "address_id")
	val addressModelId: Long,
)
