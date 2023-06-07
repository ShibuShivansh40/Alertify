package com.example.livestock_alert_1

import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET()
    suspend fun getItems(@Url URL_Captured: String): List<Item>
}
