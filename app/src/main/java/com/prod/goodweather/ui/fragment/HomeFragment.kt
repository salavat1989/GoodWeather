package com.prod.goodweather.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prod.goodweather.ui.GoodWeatherApp
import com.prod.goodweather.ui.viewModel.HomeFragmentViewModel
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    private val component by lazy {
        (requireActivity().application as GoodWeatherApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeFragmentViewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[HomeFragmentViewModel::class.java]
    }


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
}