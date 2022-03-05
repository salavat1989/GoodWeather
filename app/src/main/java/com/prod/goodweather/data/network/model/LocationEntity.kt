package com.prod.goodweather.data.network.model

class LocationEntity(
    val latitude: Double,
    val longitude: Double,
) {
    fun isBigChange(old: LocationEntity?): Boolean {
        return if (
            old == null
            || this.latitude > old.latitude + BIG_CHANGE
            || this.latitude < old.latitude - BIG_CHANGE
            || this.longitude > old.longitude + BIG_CHANGE
            || this.longitude < old.longitude - BIG_CHANGE
        ) {
            return true
        } else false
    }
    fun isSmallChange(old: LocationEntity?): Boolean {
        return if (
            old == null
            || this.latitude > old.latitude + SMALL_CHANGE
            || this.latitude < old.latitude - SMALL_CHANGE
            || this.longitude > old.longitude + SMALL_CHANGE
            || this.longitude < old.longitude - SMALL_CHANGE
        ) {
            return true
        } else false
    }
    companion object{
        private const val BIG_CHANGE = 0.001
        private const val SMALL_CHANGE = 0.0001
    }
}
