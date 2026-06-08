package com.example.robomarciano

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val splashDelayMs = 3000L
    private val handler = Handler(Looper.getMainLooper())
    private val navegarParaPrincipal = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.postDelayed(navegarParaPrincipal, splashDelayMs)
    }

    override fun onDestroy() {
        handler.removeCallbacks(navegarParaPrincipal)
        super.onDestroy()
    }
}
