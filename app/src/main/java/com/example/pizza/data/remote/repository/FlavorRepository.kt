package com.example.pizza.data.remote.repository

import com.example.pizza.data.remote.network.dto.FlavorDTO
import com.example.pizza.data.vo.Resource
import kotlinx.coroutines.flow.Flow

interface FlavorRepository {
    suspend fun getFlavors(): Flow<Resource<List<FlavorDTO>>>
}