package com.prod.goodweather.di.module

import androidx.lifecycle.ViewModel
import com.prod.goodweather.di.annotation.ViewModelKey
import com.prod.goodweather.ui.viewModel.HomeFragmentViewModel
import com.prod.goodweather.ui.viewModel.SearchFragmentViewModel
import com.prod.goodweather.ui.viewModel.SearchResultBottomSheetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
	@IntoMap
	@ViewModelKey(HomeFragmentViewModel::class)
	@Binds
	fun bindHomeFragmentViewModel(impl: HomeFragmentViewModel): ViewModel

	@IntoMap
	@ViewModelKey(SearchFragmentViewModel::class)
	@Binds
	fun bindSearchFragmentViewModel(impl: SearchFragmentViewModel): ViewModel

	@IntoMap
	@ViewModelKey(SearchResultBottomSheetViewModel::class)
	@Binds
	fun bindSearchResultBottomSheetViewModel(impl: SearchResultBottomSheetViewModel): ViewModel
}