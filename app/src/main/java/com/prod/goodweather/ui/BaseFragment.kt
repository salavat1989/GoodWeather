package com.prod.goodweather.ui

import androidx.fragment.app.Fragment
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 08.03.2022
 */

abstract class BaseFragment: Fragment() {
	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	protected val component by lazy {
		(requireActivity().application as GoodWeatherApp).component
	}
}