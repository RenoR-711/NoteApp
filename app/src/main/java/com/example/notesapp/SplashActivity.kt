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

    /**
     * Startet als erstes nach dem Konstruktor
     * Setzt das Layout und generiert alle direkt
     * benoetigten Widgets. Setzt Listener
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate activity main
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root) //1. Layout setzen

//        Handler(Looper.getMainLooper()).postDelayed({
//        startActivity(Intent(this, HomeActivity::class.java))
//        },3000)

        timer.schedule(3000) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java) //neue Aktivität starten
            startActivity(intent)
            finish()
        }
    }
    /** eine andere Schreibweise
    //    timer.schedule(object : TimerTask() {
    //        override fun run() {
    //            val intent = Intent(this@MainActivity, HomeActivity::class.java)//neue Aktivität starten
    //            startActivity(intent)
    //            finish()
    //        }
    //    }, 3000L) // 3000 milliseconds delay
     */

    // Aktivität verlassen aber nicht löschen
    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

}
