package com.prod.goodweather.di.module

import com.prod.goodweather.data.network.WeatherRepositoryImpl
import com.prod.goodweather.di.annotation.ApplicationScope
import com.prod.goodweather.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @ApplicationScope
    @Binds
    fun bindRepository(impl: WeatherRepositoryImpl): WeatherRepository
}