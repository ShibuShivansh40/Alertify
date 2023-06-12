package com.example.livestock_alert_1

import java.util.Date

data class Item(
    val id: String,
    val location: String,
    val userType : String,
    val imageUrl : String,
    val notification : String,
    val date : Date
)