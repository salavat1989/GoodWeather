package com.prod.goodweather.data.mapper

import com.prod.goodweather.data.network.model.CurrentWeatherDto
import com.prod.goodweather.domain.entity.CurrentWeather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {
    fun mapCurrentWeatherDtoToEntity(dto:CurrentWeatherDto):CurrentWeather{
        var weatherDescription : String? = null
        var iconUrl : String? = null
        if(dto.weather.size>0){
            weatherDescription = dto.weather[0].description
            iconUrl = "https://openweathermap.org/img/wn/${dto.weather[0].icon}@2x.png"
        }
        dto.weather
        return CurrentWeather(
            cityName = dto.name,
            temperature = dto.main.temp.toString(),
            minTemperature = dto.main.tempMin.toString(),
            maxTemperature = dto.main.tempMax.toString(),
            weatherDescription = weatherDescription,
            iconUrl = iconUrl
        )
    }
}