package com.prod.goodweather.data.mapper

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 20.03.2022
 */

class UnixTimeConverter @Inject constructor(
	val application: Application,
) {
	private fun convertUnixTimeToString(t: Long, timeZone: String, template: String): String {
		val dateFormat = SimpleDateFormat(template)
		val date = Date(t * 1000)
		try {
			dateFormat.timeZone = TimeZone.getTimeZone(timeZone)
		} catch (e: Exception) {
		}
		return dateFormat.format(date)
	}

	fun convertUnixTimeToDay(t: Long, timeZone: String): String {
		return convertUnixTimeToString(t, timeZone, "EEEE")
			.replaceFirstChar { it.uppercaseChar() }
	}

	fun convertUnixTimeToHour(t: Long, timeZone: String): String {
		return convertUnixTimeToString(t, timeZone, "HH")
			.replaceFirstChar { it.uppercaseChar() }
	}
}