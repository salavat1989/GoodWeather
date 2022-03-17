package com.prod.goodweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.prod.goodweather.databinding.ItemHourlyWeatherBinding
import com.prod.goodweather.domain.entity.HourlyWeather
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 06.03.2022
 */

class HourlyWeatherAdapter @Inject constructor() :
	ListAdapter<HourlyWeather, HourlyWeatherViewHolder>(HourlyWeatherDiff()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
		val binding = ItemHourlyWeatherBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return HourlyWeatherViewHolder(binding)
	}

	override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
		val item = getItem(position)
		with(holder.binding) {
			tvTemperature.text = "${item.temperature}\u00B0"
			tvTime.text = item.forecastTime
			Picasso.get().load(item.iconURL).into(imWeatherIcon)
		}
	}
}