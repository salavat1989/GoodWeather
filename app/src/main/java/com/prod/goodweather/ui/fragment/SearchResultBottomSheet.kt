package com.prod.goodweather.ui.fragment

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prod.goodweather.R
import com.prod.goodweather.databinding.ModalBottomSheetBinding
import com.prod.goodweather.ui.GoodWeatherApp
import com.prod.goodweather.ui.adapter.DailyWeatherAdapter
import com.prod.goodweather.ui.adapter.HourlyWeatherAdapter
import com.prod.goodweather.ui.viewModel.SearchResultBottomSheetViewModel
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import javax.inject.Inject


/**
 * Created by Kadyrov Salavat on 17.03.2022
 */

class SearchResultBottomSheet : BottomSheetDialogFragment() {
	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	private val viewModel: SearchResultBottomSheetViewModel by lazy {
		ViewModelProvider(this, viewModelFactory)[SearchResultBottomSheetViewModel::class.java]
	}

	private val args by navArgs<SearchResultBottomSheetArgs>()

	@Inject
	lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter

	@Inject
	lateinit var dailyWeatherAdapter: DailyWeatherAdapter

	private var _binding: ModalBottomSheetBinding? = null
	private val binding
		get() = _binding ?: throw RuntimeException("ModalBottomSheetBinding is null")

	val component by lazy {
		(requireActivity().application as GoodWeatherApp).component
			.getFragmentComponentFactory().create(args.addressModel)
	}

	override fun onAttach(context: Context) {
		component.inject(this)
		super.onAttach(context)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = ModalBottomSheetBinding.inflate(layoutInflater)

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		Log.d("onViewCreated", "onViewCreated")
		val parentView = binding.root.parent as View
		val bottomSheetBehavior = BottomSheetBehavior.from(parentView)
		bottomSheetBehavior.peekHeight = getSheetHeight()
		binding.frameLayout.layoutParams = FrameLayout
			.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getSheetHeight())

		Log.d("onViewCreated", binding.frameLayout.height.toString())
		Log.d("onViewCreated", binding.detailWeather.root.height.toString())
		setAddress()
		setRvAdapters()
		setViewModelObserve()
		super.onViewCreated(view, savedInstanceState)
	}

	private fun getSheetHeight() = (Resources.getSystem().displayMetrics.heightPixels * 0.9).toInt()

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

	private fun setTextAndVisibility(view: TextView, text: String?) {
		if (text == null) {
			view.visibility = View.GONE
		} else {
			view.text = text
			view.visibility = View.VISIBLE
		}
	}

	private fun setAddress() {
		binding.detailWeather.tvCityName.text = args.addressModel.mainAddress
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}

	companion object {
		const val TAG = "ModalBottomSheet"
	}
}