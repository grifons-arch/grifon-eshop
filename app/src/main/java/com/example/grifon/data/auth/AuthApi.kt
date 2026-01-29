package com.example.grifon.data.auth

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RequestBody): RegisterResponseDto
}
