package com.prod.goodweather.di.module

import com.prod.goodweather.data.network.ApiFactory
import com.prod.goodweather.data.network.ApiService
import com.prod.goodweather.di.annotation.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}