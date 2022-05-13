package com.example.fixphone.service

import com.example.fixphone.model.GetLatestPhoneResponse
import com.example.fixphone.model.GetPhoneSpecsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("latest")
    fun getLatestPhone(): Call<GetLatestPhoneResponse>
    @GET("{slug}")
    fun getSpecsPhoneById(@Path("slug") slug : String): Call<GetPhoneSpecsResponse>
}