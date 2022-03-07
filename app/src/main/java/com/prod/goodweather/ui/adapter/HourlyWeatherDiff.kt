package com.prod.goodweather.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.prod.goodweather.domain.entity.HourlyWeather

/**
 * Created by Kadyrov Salavat on 06.03.2022
 */

class HourlyWeatherDiff: DiffUtil.ItemCallback<HourlyWeather>() {
    override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return oldItem.unixTime == newItem.unixTime
    }

    override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
        return oldItem == newItem
    }
}