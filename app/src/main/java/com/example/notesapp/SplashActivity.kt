package com.example.notesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Android 12+ Splash API
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Set our animated logo overlay
        setContentView(R.layout.activity_splash) // kommt gleich

        val logo = findViewById<ImageView>(R.id.imageView)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        logo.startAnimation(fadeIn)

        fadeIn.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
            override fun onAnimationStart(animation: android.view.animation.Animation?) {}
            override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}

            override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }
}
