package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatDelegate
import com.example.submissionaplikasigithubuser.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        supportActionBar?.title = "Settings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        switchTheme.isChecked = sharedPrefs.getBoolean("isDarkModeActive", false)

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            saveThemeSetting(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val btnhome = findViewById<ImageButton>(R.id.home_btn)
        val btnfav = findViewById<ImageButton>(R.id.favorite_btn)
        val btnprofile = findViewById<ImageButton>(R.id.profile_btn)

        btnhome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnfav.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        btnprofile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun saveThemeSetting(isDarkModeActive: Boolean) {
        val editor = sharedPrefs.edit()
        editor.putBoolean("isDarkModeActive", isDarkModeActive)
        editor.apply()
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