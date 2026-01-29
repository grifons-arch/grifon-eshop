package com.example.grifon.data.auth

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers(
        "Content-Type: application/json; charset=utf-8",
        "Accept: application/json",
    )
    @POST("auth/register")
    suspend fun register(@Body request: RequestBody): RegisterResponseDto
}
