package com.prod.goodweather.di.module

import android.app.Application
import com.prod.goodweather.data.database.AppDao
import com.prod.goodweather.data.database.AppDatabase
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

		@ApplicationScope
		@Provides
		fun provideDao(application: Application): AppDao {
			return AppDatabase.getInstance(application).dao()
		}
	}
}