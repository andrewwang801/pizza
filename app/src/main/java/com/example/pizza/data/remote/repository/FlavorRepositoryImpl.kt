package com.example.pizza.data.remote.repository

import com.example.pizza.data.remote.network.services.FlavorService
import com.example.pizza.data.remote.ResponseHelper
import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.data.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlavorRepositoryImpl (
    private val service: FlavorService
) : FlavorRepository {
    override suspend fun getFlavors(): Flow<Resource<List<FlavorDTO>>> = flow {
        emit(ResponseHelper.getResponseResult { service.getFlavors() })
    }

}