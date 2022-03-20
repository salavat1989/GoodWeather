package com.prod.goodweather.data.database.dbmodel

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Kadyrov Salavat on 18.03.2022
 */
@Entity(tableName = "address_model",
	indices = [
		Index(value = ["id"], unique = true)]
)
data class AddressModelDb(
	@PrimaryKey(autoGenerate = true)
	var id: Long,
	val mainAddress: String,
	val subAddress: String?,
	val latitude: Double,
	val longitude: Double,
	val isCurrentLocation: Boolean,

	)
