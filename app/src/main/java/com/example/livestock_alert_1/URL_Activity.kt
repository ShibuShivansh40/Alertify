package com.example.livestock_alert_1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

lateinit var url : String

class URL_Activity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)
        var submit_button = findViewById<Button>(R.id.submit_button)
        var url_field = findViewById<EditText>(R.id.url_field)


        submit_button.setOnClickListener{
            url = url_field.text.toString()
            Log.d("URL", url)
            val i = Intent(this, MainActivity::class.java)
            i.putExtra("URL", url)
            startActivity(i)
        }


    }
}