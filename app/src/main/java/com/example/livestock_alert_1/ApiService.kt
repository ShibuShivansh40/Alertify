package com.example.livestock_alert_1

import retrofit2.http.GET

interface ApiService {
    @GET("http://4945-43-230-198-50.ngrok-free.app/all/")
    suspend fun getItems(): List<Item>
}
