package com.prod.goodweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prod.goodweather.R
import com.prod.goodweather.databinding.FragmentHomeBinding
import com.prod.goodweather.ui.GoodWeatherApp
import com.prod.goodweather.ui.adapter.DailyWeatherAdapter
import com.prod.goodweather.ui.adapter.HourlyWeatherAdapter
import com.prod.goodweather.ui.viewModel.HomeFragmentViewModel
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import com.prod.goodweather.utils.LiveCurrentLocation
import com.prod.goodweather.utils.checkAccessFineLocationPermission
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {
	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val component by lazy {
		(requireActivity().application as GoodWeatherApp).component
			.getFragmentComponentFactory().create(null)
	}

	@Inject
	lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

	@Inject
	lateinit var dailyWeatherAdapter: DailyWeatherAdapter

	@Inject
	lateinit var currentLocation: LiveCurrentLocation


	private val viewModel: HomeFragmentViewModel by lazy {
		ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]
	}

	private var _binding: FragmentHomeBinding? = null
	private val binding
		get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

	private val scope = CoroutineScope(Dispatchers.Main)
	override fun onAttach(context: Context) {
		component.inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = FragmentHomeBinding.inflate(layoutInflater)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setRvAdapters()
		setViewModelObserve()
		checkAndWaitPermission()
		super.onViewCreated(view, savedInstanceState)
	}

	private fun setRvAdapters() {
		with(binding.detailWeather) {
			rvHourlyWeather.adapter = hourlyWeatherAdapter
			rvDailyWeather.layoutManager =
				object : LinearLayoutManager(requireActivity().application) {
					override fun canScrollVertically() = false
				}
			rvDailyWeather.adapter = dailyWeatherAdapter
		}
	}

	private fun setViewModelObserve() {
		viewModel.weather.observe(viewLifecycleOwner) {
			it?.let {
				with(binding.detailWeather) {
					tvTemperature.text = "${it.temperature}\u00B0"
					setTextAndVisibility(
						tvWeatherDescription,
						it.weatherDescription
					)
					setTextAndVisibility(
						tvFeelsLike,
						String.format(requireActivity().getString(R.string.feels_like),
							it.feelsLike + "\u00B0")
					)
					Picasso.get().load(it.iconUrl).into(imWeatherIcon)
					hourlyWeatherAdapter.submitList(it.listHourlyWeather)
					dailyWeatherAdapter.submitList(it.listDailyWeather)

				}
			}
		}
		viewModel.address.observe(viewLifecycleOwner) {
			it?.let {
				with(binding.detailWeather) {
					tvCityName.text = it.mainAddress
					setTextAndVisibility(
						tvAddress,
						it.subAddress
					)
				}
			}
		}
	}

	private fun setTextAndVisibility(view: TextView, text: String?) {
		if (text == null) {
			view.visibility = View.GONE
		} else {
			view.text = text
			view.visibility = View.VISIBLE
		}
	}

	private fun checkAndWaitPermission() {
		if (requireActivity().checkAccessFineLocationPermission()) {
			observeLocationUpdate()
		} else {
			scope.launch {
				delay(3000)
				checkAndWaitPermission()
			}
		}
	}

	private fun observeLocationUpdate() {
		currentLocation.observe(viewLifecycleOwner) {
			viewModel.locationChanged(it)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}