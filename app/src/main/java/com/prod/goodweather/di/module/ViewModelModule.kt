package com.prod.goodweather.di.module

import androidx.lifecycle.ViewModel
import com.prod.goodweather.di.annotation.ViewModelKey
import com.prod.goodweather.ui.viewModel.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    @Binds
    fun bindHomeFragmentViewModel(impl: HomeFragmentViewModel): ViewModel
}