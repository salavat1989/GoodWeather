package com.prod.goodweather.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.prod.goodweather.R
import com.prod.goodweather.databinding.FragmentHomeBinding
import com.prod.goodweather.ui.GoodWeatherApp
import com.prod.goodweather.ui.viewModel.HomeFragmentViewModel
import com.prod.goodweather.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import java.util.*
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
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.weather.observe(viewLifecycleOwner){
            binding.tvTemperature.text = "${it.temperature}\u00B0"
            binding.tvWeatherDescription.text = it.weatherDescription
            binding.tvMinMaxTemperature.text = String.format(this.getString(R.string.min_max_temperature),it.minTemperature+"\u00B0",it.maxTemperature+"\u00B0")
            Picasso.get().load(it.iconUrl).into(binding.imWeatherIcon)
        }
        viewModel.address.observe(viewLifecycleOwner){
            binding.tvCityName.text = it.mainAddress
            binding.tvAddress.text = it.subAddress
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