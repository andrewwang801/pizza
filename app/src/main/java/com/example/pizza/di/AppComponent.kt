package com.example.pizza.di

import com.example.pizza.PizzaApp
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidModule::class,
        FlavorsModule::class,
        ViewModelsModule::class
    ]
)
interface AppComponent {
    fun inject(app: PizzaApp)
}