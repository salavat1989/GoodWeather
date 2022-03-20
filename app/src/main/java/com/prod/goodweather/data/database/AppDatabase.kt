package com.prod.goodweather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prod.goodweather.data.database.dbmodel.AddressModelDb
import com.prod.goodweather.data.database.dbmodel.DailyWeatherDb
import com.prod.goodweather.data.database.dbmodel.HourlyWeatherDb
import com.prod.goodweather.data.database.dbmodel.WeatherDb

/**
 * Created by Kadyrov Salavat on 18.03.2022
 */
@Database(entities = [
	AddressModelDb::class,
	WeatherDb::class,
	DailyWeatherDb::class,
	HourlyWeatherDb::class,
],
	version = 1,
	exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
	companion object {
		private var db: AppDatabase? = null
		private const val DB_NAME = "main.db"
		private val LOCK = Any()
		fun getInstance(context: Context): AppDatabase {
			synchronized(LOCK) {
				db?.let {
					return it
				}
				val instance = Room.databaseBuilder(
					context,
					AppDatabase::class.java,
					DB_NAME
				).build()
				db = instance
				return instance
			}
		}
	}

	abstract fun dao(): AppDao
}