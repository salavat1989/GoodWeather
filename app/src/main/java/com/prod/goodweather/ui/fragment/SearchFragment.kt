package com.prod.goodweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prod.goodweather.databinding.FragmentSearchBinding
import com.prod.goodweather.domain.entity.AddressModel
import com.prod.goodweather.ui.GoodWeatherApp
import com.prod.goodweather.ui.MainActivity
import com.prod.goodweather.ui.adapter.LocationSearchResultAdapter
import com.prod.goodweather.ui.viewModel.SearchFragmentViewModel
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import javax.inject.Inject

/**
 * Created by Kadyrov Salavat on 07.03.2022
 */


class SearchFragment : Fragment() {
	@Inject
	lateinit var viewModelFactory: ViewModelFactory

	val component by lazy {
		(requireActivity().application as GoodWeatherApp).component
			.getFragmentComponentFactory().create(null)
	}
	private var _binding: FragmentSearchBinding? = null
	val binding
		get() = _binding ?: throw RuntimeException("SearchFragmentBinding is null")

	@Inject
	lateinit var locationSearchResultAdapter: LocationSearchResultAdapter

	private val viewModel: SearchFragmentViewModel by lazy {
		ViewModelProvider(this, viewModelFactory)[SearchFragmentViewModel::class.java]
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
		_binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setRvAdapter()
		setViewModelObserve()
		binding.etSearch.addTextChangedListener {
			viewModel.searchLocationFromSubString(it.toString())
		}
	}

	private fun setViewModelObserve() {
		viewModel.searchResultLocations.observe(viewLifecycleOwner) {
			locationSearchResultAdapter.submitList(it)
		}
	}

	private fun setRvAdapter() {
		locationSearchResultAdapter.onClick = {
			launchBottomSheet(it)
		}
		binding.rvSearchResult.adapter = locationSearchResultAdapter
	}

	private fun launchBottomSheet(address: AddressModel) {
		val action = SearchFragmentDirections
			.actionSearchFragmentToSearchResultBottomSheet(address)
		(requireActivity() as MainActivity).navController.navigate(action)
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}