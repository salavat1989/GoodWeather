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
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeFragment : Fragment() {
	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val component by lazy {
		(requireActivity().application as GoodWeatherApp).component
	}

	@Inject
	lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

	@Inject
	lateinit var dailyWeatherAdapter: DailyWeatherAdapter


	private val viewModel: HomeFragmentViewModel by lazy {
		ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]
	}

	private var _binding: FragmentHomeBinding? = null
	private val binding
		get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

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
		super.onViewCreated(view, savedInstanceState)
	}

	private fun setRvAdapters() {
		binding.rvHourlyWeather.adapter = hourlyWeatherAdapter
		binding.rvDailyWeather.layoutManager =
			object : LinearLayoutManager(requireActivity().application) {
				override fun canScrollVertically() = false
			}
		binding.rvDailyWeather.adapter = dailyWeatherAdapter
	}

	private fun setViewModelObserve() {
		viewModel.weather.observe(viewLifecycleOwner) {
			binding.tvTemperature.text = "${it.temperature}\u00B0"
			setTextAndVisibility(
				binding.tvWeatherDescription,
				it.weatherDescription
			)
			setTextAndVisibility(
				binding.tvFeelsLike,
				String.format(this.getString(R.string.feels_like), it.feelsLike + "\u00B0")
			)
			Picasso.get().load(it.iconUrl).into(binding.imWeatherIcon)
			hourlyWeatherAdapter.submitList(it.listHourlyWeather)
			dailyWeatherAdapter.submitList(it.listDailyWeather)
		}
		viewModel.address.observe(viewLifecycleOwner) {
			binding.tvCityName.text = it.mainAddress
			setTextAndVisibility(
				binding.tvAddress,
				it.subAddress
			)
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

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}