package com.prod.goodweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.prod.goodweather.databinding.SearchResultItemBinding
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

class LocationSearchResultAdapter @Inject constructor() :
	ListAdapter<AddressModel, LocationSearchResultViewHolder>(LocationSearchResultDiff()) {
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int,
	): LocationSearchResultViewHolder {
		val binding = SearchResultItemBinding.inflate(LayoutInflater.from(parent.context),
			parent,
			false
		)
		return LocationSearchResultViewHolder(binding)
	}

	override fun onBindViewHolder(holder: LocationSearchResultViewHolder, position: Int) {
		holder.binding.cityName.text = getItem(position).addressLine
	}
}