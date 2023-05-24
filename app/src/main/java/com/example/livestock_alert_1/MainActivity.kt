package com.example.livestock_alert_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        handler = Handler(Looper.getMainLooper())
        recyclerView = findViewById<RecyclerView>(R.id.recylcer_view)
        adapter = ItemAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://4945-43-230-198-50.ngrok-free.app/all/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        Log.d("Main_Activity" , retrofit.toString())
        apiService = retrofit.create(ApiService::class.java)
    }

    override fun onResume() {
        super.onResume()
        handler.post(apiRunnable) // Start fetching data when the activity resumes
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(apiRunnable) // Stop fetching data when the activity pauses
    }

    private val apiRunnable = object : Runnable {
        override fun run() {
            fetchDataFromApi()
            handler.postDelayed(this, 60*10000) // Schedule the next API call after 10 seconds
        }
    }

    private fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val items = apiService.getItems()
                adapter.updateData(items)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
