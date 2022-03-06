package com.prod.goodweather.data.network.model


import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("alerts")
    val alerts: List<Alert?>?,
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>?,
    @SerializedName("hourly")
    val hourly: List<Hourly>?,
    @SerializedName("lat")
    val lat: Double?, // 51.5098
    @SerializedName("lon")
    val lon: Double?, // 47.118
    @SerializedName("timezone")
    val timezone: String?, // Europe/Saratov
    @SerializedName("timezone_offset")
    val timezoneOffset: Int? // 14400
) {
    data class Alert(
        @SerializedName("description")
        val description: String?,
        @SerializedName("end")
        val end: Long?, // 1646582400
        @SerializedName("event")
        val event: String?, // Wind
        @SerializedName("sender_name")
        val senderName: String?,
        @SerializedName("start")
        val start: Long?, // 1646506800
        @SerializedName("tags")
        val tags: List<String?>?
    )

    data class Current(
        @SerializedName("clouds")
        val clouds: Double?, // 100
        @SerializedName("dew_point")
        val dewPoint: Double?, // -4.92
        @SerializedName("dt")
        val dt: Int?, // 1646510445
        @SerializedName("feels_like")
        val feelsLike: Double?, // -9.86
        @SerializedName("humidity")
        val humidity: Double?, // 84
        @SerializedName("pressure")
        val pressure: Double?, // 1000
        @SerializedName("snow")
        val snow: Snow?,
        @SerializedName("sunrise")
        val sunrise: Long?, // 1646537198
        @SerializedName("sunset")
        val sunset: Long?, // 1646577563
        @SerializedName("temp")
        val temp: Double, // -2.86
        @SerializedName("uvi")
        val uvi: Double?, // 0
        @SerializedName("visibility")
        val visibility: Double?, // 10000
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Double?, // 349
        @SerializedName("wind_gust")
        val windGust: Double?, // 15.1
        @SerializedName("wind_speed")
        val windSpeed: Double? // 9.18
    ) {
        data class Snow(
            @SerializedName("1h")
            val h: Double? // 0.15
        )

        data class Weather(
            @SerializedName("description")
            val description: String?, // light snow
            @SerializedName("icon")
            val icon: String?, // 13n
            @SerializedName("id")
            val id: Int?, // 600
            @SerializedName("main")
            val main: String? // Snow
        )
    }

    data class Daily(
        @SerializedName("clouds")
        val clouds: Int?, // 10
        @SerializedName("dew_point")
        val dewPoint: Double?, // -11.65
        @SerializedName("dt")
        val dt: Long?, // 1646557200
        @SerializedName("feels_like")
        val feelsLike: FeelsLike?,
        @SerializedName("humidity")
        val humidity: Double?, // 81
        @SerializedName("moon_phase")
        val moonPhase: Double?, // 0.12
        @SerializedName("moonrise")
        val moonrise: Long?, // 1646542860
        @SerializedName("moonset")
        val moonset: Long?, // 1646593860
        @SerializedName("pop")
        val pop: Double?, // 0.63
        @SerializedName("pressure")
        val pressure: Double?, // 1011
        @SerializedName("snow")
        val snow: Double?, // 0.15
        @SerializedName("sunrise")
        val sunrise: Long?, // 1646537198
        @SerializedName("sunset")
        val sunset: Long?, // 1646577563
        @SerializedName("temp")
        val temp: Temp?,
        @SerializedName("uvi")
        val uvi: Double?, // 2.88
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Double?, // 349
        @SerializedName("wind_gust")
        val windGust: Double?, // 15.1
        @SerializedName("wind_speed")
        val windSpeed: Double? // 9.18
    ) {
        data class FeelsLike(
            @SerializedName("day")
            val day: Double?, // -15.5
            @SerializedName("eve")
            val eve: Double?, // -17.33
            @SerializedName("morn")
            val morn: Double?, // -16.91
            @SerializedName("night")
            val night: Double? // -21.11
        )

        data class Temp(
            @SerializedName("day")
            val day: Double?, // -8.5
            @SerializedName("eve")
            val eve: Double?, // -10.9
            @SerializedName("max")
            val max: Double?, // -2.86
            @SerializedName("min")
            val min: Double?, // -14.33
            @SerializedName("morn")
            val morn: Double?, // -9.91
            @SerializedName("night")
            val night: Double? // -14.33
        )

        data class Weather(
            @SerializedName("description")
            val description: String?, // light snow
            @SerializedName("icon")
            val icon: String?, // 13d
            @SerializedName("id")
            val id: Int?, // 600
            @SerializedName("main")
            val main: String? // Snow
        )
    }

    data class Hourly(
        @SerializedName("clouds")
        val clouds: Double?, // 100
        @SerializedName("dew_point")
        val dewPoint: Double?, // -4.92
        @SerializedName("dt")
        val dt: Long, // 1646510400
        @SerializedName("feels_like")
        val feelsLike: Double?, // -9.86
        @SerializedName("humidity")
        val humidity: Double?, // 84
        @SerializedName("pop")
        val pop: Double?, // 0.63
        @SerializedName("pressure")
        val pressure: Double?, // 1000
        @SerializedName("snow")
        val snow: Snow?,
        @SerializedName("temp")
        val temp: Double, // -2.86
        @SerializedName("uvi")
        val uvi: Double?, // 0
        @SerializedName("visibility")
        val visibility: Double?, // 10000
        @SerializedName("weather")
        val weather: List<Weather?>?,
        @SerializedName("wind_deg")
        val windDeg: Double?, // 349
        @SerializedName("wind_gust")
        val windGust: Double?, // 15.1
        @SerializedName("wind_speed")
        val windSpeed: Double? // 9.18
    ) {
        data class Snow(
            @SerializedName("1h")
            val h: Double? // 0.15
        )

        data class Weather(
            @SerializedName("description")
            val description: String?, // light snow
            @SerializedName("icon")
            val icon: String?, // 13n
            @SerializedName("id")
            val id: Int?, // 600
            @SerializedName("main")
            val main: String? // Snow
        )
    }
}