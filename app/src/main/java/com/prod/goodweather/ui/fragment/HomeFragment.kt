package com.prod.goodweather.ui.fragment

import androidx.fragment.app.Fragment
import com.prod.goodweather.ui.GoodWeatherApp
import javax.inject.Inject

class HomeFragment: Fragment() {
    private val component by lazy {
        (requireActivity().application as GoodWeatherApp).component
    }
}