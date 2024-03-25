package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.submissionaplikasigithubuser.R

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIMEOUT: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        val homeIntent = Intent(this, MainActivity::class.java)

        Thread {
            try {
                Thread.sleep(SPLASH_TIMEOUT)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                startActivity(homeIntent)
                finish()
            }
        }.start()
    }
}
