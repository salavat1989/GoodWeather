package com.prod.goodweather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prod.goodweather.databinding.SearchFragmentBinding

/**
 * Created by Kadyrov Salavat on 07.03.2022
 */

class SearchFragment : Fragment() {
	var _binding: SearchFragmentBinding? = null
	val binding
		get() = _binding ?: throw RuntimeException("SearchFragmentBinding is null")

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = SearchFragmentBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}