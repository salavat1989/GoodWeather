package com.prod.goodweather.data.network.model

class LocationEntity(
    val latitude: Double,
    val longitude: Double,
) {
    fun isBigChange(old: LocationEntity?): Boolean {
        return if (
            old == null
            || this.latitude > old.latitude + 0.0001
            || this.latitude < old.latitude - 0.0001
            || this.longitude > old.longitude + 0.0001
            || this.longitude < old.longitude - 0.0001
        ) {
            return true
        } else false
    }
}
