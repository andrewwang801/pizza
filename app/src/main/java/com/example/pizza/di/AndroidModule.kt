package com.example.pizza.di

import com.example.pizza.ui.flavors.FlavorsFragment
import com.example.pizza.ui.summary.SummaryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {
    @ContributesAndroidInjector
    abstract fun flavorsFragment(): FlavorsFragment

    @ContributesAndroidInjector
    abstract fun summaryFragment(): SummaryFragment
}