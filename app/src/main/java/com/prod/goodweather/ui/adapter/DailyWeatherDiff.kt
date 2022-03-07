package com.prod.goodweather.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.prod.goodweather.domain.entity.DailyWeather

/**
 * Created by Kadyrov Salavat on 07.03.2022
 */

class DailyWeatherDiff : DiffUtil.ItemCallback<DailyWeather>() {
    override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem.unixTime == newItem.unixTime
    }

    override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean {
        return oldItem == newItem
    }
}