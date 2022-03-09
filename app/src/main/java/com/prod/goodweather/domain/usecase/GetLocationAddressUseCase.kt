package com.prod.goodweather.domain.usecase

import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.entity.LocationModel
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetLocationAddressUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    suspend operator fun invoke(location: LocationModel): AddressModel {
        return repository.getLocationAddress(location)
    }
}