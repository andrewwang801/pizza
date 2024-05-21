package com.example.pizza.data.remote

import com.example.pizza.data.vo.Resource
import retrofit2.Response

class ResponseHelper {
    companion object {
        suspend fun <T> getResponseResult(call: suspend () -> Response<T>): Resource<T> {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return Resource.success(body)
                }
                return throwError("${response.code()}: ${response.message()}")
            } catch (e: Exception) {
                return throwError(e.message ?: "No internet connection")
            } finally {
            }
        }

        private fun <T> throwError(message: String): Resource<T> =
            Resource.error("Network call has failed for a following reason: $message")
    }
}