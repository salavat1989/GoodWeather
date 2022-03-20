package com.prod.goodweather.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prod.goodweather.data.database.dbmodel.*


/**
 * Created by Kadyrov Salavat on 18.03.2022
 */
@Dao
interface AppDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAddress(db: AddressModelDb): Long

	@Query("Select * from address_model where isCurrentLocation = 1 Limit 1")
	fun getCurrentLocationLive(): LiveData<AddressModelDb?>

	@Query("Select * from address_model where isCurrentLocation = 1 Limit 1")
	suspend fun getCurrentLocation(): AddressModelDb?

	@Query("Delete from weather where address_id=:id")
	suspend fun deleteWeatherById(id: Long)

	@Transaction
	suspend fun insertAllWeather(allWeatherDb: AllWeatherDb) {
		val weatherId = insertWeather(allWeatherDb.weatherDb)
		for (hourly in allWeatherDb.listHourlyWeatherDb) {
			hourly.weatherId = weatherId
			insertHourlyWeather(hourly)
		}
		for (daily in allWeatherDb.listDailyWeatherDb) {
			daily.weatherId = weatherId
			insertDailyWeather(daily)
		}
	}

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertWeather(weatherDb: WeatherDb): Long

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertHourlyWeather(hourlyWeatherDb: HourlyWeatherDb): Long

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertDailyWeather(dailyWeatherDb: DailyWeatherDb): Long

	@Transaction
	@Query("SELECT * FROM weather JOIN " +
			"(SELECT id as adrId FROM address_model where isCurrentLocation = 1 ) as address " +
			"ON weather.address_id = address.adrId Limit 1")
	fun getCurrentLocationAllWeather(): LiveData<AllWeatherDb>


}