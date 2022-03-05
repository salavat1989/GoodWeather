package com.prod.goodweather.domain.usecase

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.LocalityAddress
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val repository: WeatherRepository,
) {
    operator fun invoke(): LiveData<LocalityAddress> {
        return repository.getCurrentLocationAddress()
    }
}