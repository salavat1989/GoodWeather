package com.prod.goodweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prod.goodweather.databinding.FragmentHomeBinding
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
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.weather.observe(viewLifecycleOwner){
            binding.cityName.text = it.name
            binding.temperature.text = it.main.temp.toString()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}