package com.prod.goodweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.prod.goodweather.databinding.ItemDailyWeatherBinding
import com.prod.goodweather.domain.entity.DailyWeather
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 07.03.2022
 */

class DailyWeatherAdapter @Inject constructor() :
	ListAdapter<DailyWeather, DailyWeatherViewHolder>(DailyWeatherDiff()) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
		val binding = ItemDailyWeatherBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return DailyWeatherViewHolder(binding)
	}

	override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
		val item = getItem(position)
		with(holder.binding) {

			tvDayName.text = item.forecastTime
			tvMaxTemp.text = "${item.temperatureMax}\u00B0"
			tvMinTemp.text = "${item.temperatureMin}\u00B0"
			Picasso.get().load(item.iconURL).into(imWeatherIcon)
		}
	}
}