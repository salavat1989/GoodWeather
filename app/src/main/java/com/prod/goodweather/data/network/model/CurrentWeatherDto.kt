package com.prod.goodweather.data.network.model
import com.google.gson.annotations.SerializedName


data class CurrentWeatherDto(
    @SerializedName("base")
    val base: String, // stations
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int, // 200
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int, // 1560350645
    @SerializedName("id")
    val id: Int, // 420006353
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String, // Mountain View
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int, // -25200
    @SerializedName("visibility")
    val visibility: Int, // 16093
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    data class Clouds(
        @SerializedName("all")
        val all: Int // 1
    )

    data class Coord(
        @SerializedName("lat")
        val lat: Double, // 37.39
        @SerializedName("lon")
        val lon: Double // -122.08
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double, // 281.86
        @SerializedName("humidity")
        val humidity: Int, // 100
        @SerializedName("pressure")
        val pressure: Int, // 1023
        @SerializedName("temp")
        val temp: Double, // 282.55
        @SerializedName("temp_max")
        val tempMax: Double, // 284.26
        @SerializedName("temp_min")
        val tempMin: Double // 280.37
    )

    data class Sys(
        @SerializedName("country")
        val country: String, // US
        @SerializedName("id")
        val id: Int, // 5122
        @SerializedName("message")
        val message: Double, // 0.0139
        @SerializedName("sunrise")
        val sunrise: Int, // 1560343627
        @SerializedName("sunset")
        val sunset: Int, // 1560396563
        @SerializedName("type")
        val type: Int // 1
    )

    data class Weather(
        @SerializedName("description")
        val description: String, // clear sky
        @SerializedName("icon")
        val icon: String, // 01d
        @SerializedName("id")
        val id: Int, // 800
        @SerializedName("main")
        val main: String // Clear
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Int, // 350
        @SerializedName("speed")
        val speed: Double // 1.5
    )
}