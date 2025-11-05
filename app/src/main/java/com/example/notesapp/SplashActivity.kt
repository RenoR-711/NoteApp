package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.splashImage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                // WebP aus /res/drawable(-nodpi)/ laden
                val source = ImageDecoder.createSource(resources, R.drawable.animation)
                val drawable = ImageDecoder.decodeDrawable(source)  // rote Linie, nur eine IDE-Hinweis, kein Fehler @suppress

                imageView.setImageDrawable(drawable)

                if (drawable is AnimatedImageDrawable) {
                    drawable.start()
                    // ~70 Frames @33ms = ~2310ms -> kleiner Puffer
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (drawable.isRunning) drawable.stop()
                        goNext()
                    }, 3000)
                } else {
                    // Statisches Drawable erhalten (kein animiertes WebP o.ä.)
                    Handler(Looper.getMainLooper()).postDelayed({ goNext() }, 1200)
                }
            } catch (e: Throwable) {
                // Fallback: irgendwas lief schief (Datei fehlt/Format)
                imageView.setImageResource(R.drawable.branding_logo)
                Handler(Looper.getMainLooper()).postDelayed({ goNext() }, 1200)
            }
        } else {
            // API < 28: animiertes WebP wird nicht unterstützt
            imageView.setImageResource(R.drawable.branding_logo)
            Handler(Looper.getMainLooper()).postDelayed({ goNext() }, 1200)
        }
    }

    private fun goNext() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
