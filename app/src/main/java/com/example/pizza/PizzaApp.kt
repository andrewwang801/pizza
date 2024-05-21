package com.example.pizza

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

import com.example.pizza.di.DaggerAppComponent

class PizzaApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .build().run {
                inject(this@PizzaApp)
            }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
