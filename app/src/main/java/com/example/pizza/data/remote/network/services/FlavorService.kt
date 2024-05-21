package com.example.pizza.data.remote.network.services

import com.example.pizza.data.remote.network.dto.FlavorDTO
import retrofit2.Response
import retrofit2.http.GET

interface FlavorService {
    @GET("mobile/tests/pizzas.json")
    suspend fun getFlavors() : Response<List<FlavorDTO>>
}