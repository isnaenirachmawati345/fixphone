package com.example.fixphone.service

class ApiHelper (private val apiService: ApiService){
    suspend fun getLatestPhone() = apiService.getLatestPhone()
    suspend fun getSpecsPhoneById(slug: String) = apiService.getSpecsPhoneById(slug)
}