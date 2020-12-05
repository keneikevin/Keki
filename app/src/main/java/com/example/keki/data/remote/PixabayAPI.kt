package com.example.keki.data.remote


import com.example.keki.BuildConfig
import com.example.keki.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {
    @GET("api/")
    suspend fun searchForImage(
    @Query("q") searchQuery:String,
    @Query("key") api: String = BuildConfig.API_KEY
    ): Response<ImageResponse>

}