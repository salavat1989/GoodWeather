package com.prod.goodweather.di

import android.app.Application
import com.prod.goodweather.di.annotation.ApplicationScope
import com.prod.goodweather.ui.fragment.HomeFragment
import dagger.BindsInstance
import dagger.Component
@ApplicationScope
@Component
interface ApplicationComponent {
    fun inject(fragment : HomeFragment)
    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}