package com.prod.goodweather.data.network.model

data class LocalityAddressDto(
    val countryName: String,
    val adminArea: String?,
    val subAdminArea: String?,
    val locality: String?,
    val thoroughfare: String?,
)
