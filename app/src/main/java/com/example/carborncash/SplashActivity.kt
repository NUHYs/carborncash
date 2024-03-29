package com.example.carborncash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.carborncash.fragment.MainFragment

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext, MainFragment::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}