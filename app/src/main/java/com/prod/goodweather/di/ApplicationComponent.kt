package com.prod.goodweather.di

import android.app.Application
import com.prod.goodweather.di.annotation.ApplicationScope
import com.prod.goodweather.di.module.DataModule
import com.prod.goodweather.di.module.DomainModule
import com.prod.goodweather.di.module.ViewModelModule
import com.prod.goodweather.ui.fragment.HomeFragment
import com.prod.goodweather.ui.fragment.SearchFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    ViewModelModule::class,
    DataModule::class,
    DomainModule::class
])
interface ApplicationComponent {
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}