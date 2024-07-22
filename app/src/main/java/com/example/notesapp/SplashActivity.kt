package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapp.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    //region 1 Lebenszyklus

    private lateinit var binding: ActivitySplashBinding
    val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate activity main
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root) //1. Set layout


        timer.schedule(3000) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java) //start new activity
            startActivity(intent)
            finish()
        }
    }

    // Leave activity but do not delete it
    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

}
