package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import com.example.submissionaplikasigithubuser.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnhome = findViewById<ImageButton>(R.id.home_btn)
        val btnfav = findViewById<ImageButton>(R.id.favorite_btn)
        val btnsettings = findViewById<ImageButton>(R.id.setting_btn)

        btnhome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnfav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        btnsettings.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}