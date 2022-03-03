package com.prod.goodweather.ui

import android.app.Application
import com.prod.goodweather.di.DaggerApplicationComponent

class GoodWeatherApp:Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}