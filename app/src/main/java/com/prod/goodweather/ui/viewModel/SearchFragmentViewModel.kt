package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.domain.usecase.GetLocationsFromSubStringUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

class SearchFragmentViewModel @Inject constructor(
	private val getLocationsFromSubString: GetLocationsFromSubStringUseCase,
) : ViewModel() {
	val searchResultLocations = MutableLiveData<List<AddressModel>>()

	fun searchLocationFromSubString(subString: String) {
		if (subString.length > 3) {
			viewModelScope.launch(Dispatchers.IO) {
				val list = getLocationsFromSubString(subString)
				searchResultLocations.postValue(list)
			}
		}
		else
			searchResultLocations.postValue(emptyList())
	}
}