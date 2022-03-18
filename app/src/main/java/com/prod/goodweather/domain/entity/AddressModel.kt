package com.prod.goodweather.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressModel(
    val mainAddress: String,
    val subAddress: String?,
    val addressLine: String,
    val location: LocationModel
):Parcelable
