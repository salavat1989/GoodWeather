package com.prod.goodweather.ui.viewModel

import androidx.lifecycle.ViewModel
import com.prod.goodweather.utils.LiveCurrentLocation
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(val currentLocation: LiveCurrentLocation ):ViewModel() {

}