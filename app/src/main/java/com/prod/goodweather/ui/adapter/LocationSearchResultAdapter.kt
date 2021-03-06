package com.prod.goodweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.prod.goodweather.databinding.ItemSearchResultBinding
import com.prod.goodweather.domain.entity.AddressModel
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

class LocationSearchResultAdapter @Inject constructor() :
	ListAdapter<AddressModel, LocationSearchResultViewHolder>(LocationSearchResultDiff()) {
	var onClick: ((AddressModel) -> Unit)? = null
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int,
	): LocationSearchResultViewHolder {
		val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context),
			parent,
			false
		)
		return LocationSearchResultViewHolder(binding)
	}

	override fun onBindViewHolder(holder: LocationSearchResultViewHolder, position: Int) {
		holder.binding.cityName.text = getItem(position).addressLine
		holder.binding.root.setOnClickListener {
			onClick?.invoke(getItem(position))
		}
	}
}