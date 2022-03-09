package com.prod.goodweather.domain.entity

data class AddressModel(
    val mainAddress: String,
    val subAddress: String?,
    val addressLine: String,
    val location: LocationModel
)
