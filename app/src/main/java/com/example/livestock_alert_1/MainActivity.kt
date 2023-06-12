package com.example.livestock_alert_1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.START_STICKY
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

public lateinit var URL_Captured : String
lateinit var apiService: ApiService
class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        URL_Captured = intent.getStringExtra("URL").toString()

        handler = Handler(Looper.getMainLooper())
        recyclerView = findViewById<RecyclerView>(R.id.recylcer_view)
//        adapter = ItemAdapter(emptyList())
        adapter = ItemAdapter(this, emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("${URL_Captured}all/")
//            .baseUrl("http://4945-43-230-198-50.ngrok-free.app/all/")
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
//        handler.removeCallbacks(apiRunnable) // Stop fetching data when the activity pauses
    }

    private val apiRunnable = object : Runnable {
        override fun run() {
            fetchDataFromApi()
            handler.postDelayed(this, 10000) // Schedule the next API call after 10 seconds
        }
    }
    private fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val items = apiService.getItems("${URL_Captured}all/")
                adapter.updateData(items)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
fun showNotification(context: Context) {

    val nm : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    nm.createNotificationChannel(NotificationChannel("first", "default", NotificationManager.IMPORTANCE_DEFAULT))
    val simpleNotification = NotificationCompat.Builder(context, "first")
        .setContentTitle("LIVESTOCK ALERT")
        .setContentText(notification_response)
        .setSmallIcon(R.drawable.notification_icon)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    nm.notify(1,simpleNotification)
}
