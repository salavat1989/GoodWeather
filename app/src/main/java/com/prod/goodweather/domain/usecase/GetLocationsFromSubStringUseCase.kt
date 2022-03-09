package com.prod.goodweather.domain.usecase

import androidx.lifecycle.LiveData
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

class GetLocationsFromSubStringUseCase @Inject constructor(val repository: WeatherRepository) {
    suspend operator fun invoke(subString: String):List<AddressModel>{
    	return repository.getLocationsFromSubString(subString)
    }
}