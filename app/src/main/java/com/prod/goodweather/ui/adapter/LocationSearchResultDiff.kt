package com.prod.goodweather.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.prod.goodweather.domain.entity.AddressModel

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

class LocationSearchResultDiff:DiffUtil.ItemCallback<AddressModel>() {
	override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
		return oldItem.addressLine == newItem.addressLine
	}
}