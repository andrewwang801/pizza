package com.example.pizza.di

import com.example.pizza.data.remote.network.services.FlavorService
import com.example.pizza.data.remote.repository.FlavorRepository
import com.example.pizza.data.remote.repository.FlavorRepositoryImpl

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class FlavorsModule {

    @Singleton
    @Provides
    fun provideFlavorService(): FlavorService {
        return Retrofit.Builder()
            .baseUrl("https://static.mozio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlavorService::class.java)
    }

    @Singleton
    @Provides
    fun provideFlavorRepository(service: FlavorService): FlavorRepository = FlavorRepositoryImpl(service)
}