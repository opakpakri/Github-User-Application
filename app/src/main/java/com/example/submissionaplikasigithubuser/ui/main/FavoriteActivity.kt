package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionaplikasigithubuser.R
import com.example.submissionaplikasigithubuser.adapter.FavoriteUserAdapter
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import com.example.submissionaplikasigithubuser.databinding.ActivityFavoriteBinding
import com.example.submissionaplikasigithubuser.viewmodel.FavoriteViewModel
import com.example.submissionaplikasigithubuser.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        adapter = FavoriteUserAdapter()
        setupRecyclerView()

        val btnhome = findViewById<ImageButton>(R.id.home_btn)
        val btnsettings = findViewById<ImageButton>(R.id.setting_btn)
        val btnprofile = findViewById<ImageButton>(R.id.profile_btn)

        btnhome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnsettings.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }

        btnprofile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite"
        val settingsItem = menu.findItem(R.id.menu_setting)
        menu.findItem(R.id.menu_search)?.isVisible = false
        settingsItem.setOnMenuItemClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
            true
        }
        return true
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

    private fun setupRecyclerView() {
        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter
        binding?.progressBarFavorite?.visibility = View.VISIBLE

        val FavoriteViewmodel = obtainViewModel(this@FavoriteActivity)
        FavoriteViewmodel.getallFavoriteUsers().observe(this) { favoriteUsers ->
            binding?.progressBarFavorite?.visibility = View.GONE
            if (favoriteUsers != null && favoriteUsers.isNotEmpty()) {
                adapter.setListData(favoriteUsers)
                binding?.tvEmpty?.visibility = View.GONE
                binding?.givEmptyUser?.visibility = View.GONE
            } else {
                binding?.tvEmpty?.visibility = View.VISIBLE
                binding?.givEmptyUser?.visibility = View.VISIBLE
                binding?.rvUser?.visibility = View.GONE
            }
        }

        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                selectedUser(data)
            }
        })
    }

    private fun handleVisibility(view: View, visibility: Int) {
        view.visibility = visibility
    }
    private fun selectedUser(data: FavoriteUser) {
        startActivityWithExtra(UserDetailActivity::class.java, UserDetailActivity.EXTRA_USER, data.login)
    }

    private fun startActivityWithExtra(activityClass: Class<*>, extraKey: String, extraValue: String) {
        val intent = Intent(this, activityClass)
        intent.putExtra(extraKey, extraValue)
        startActivity(intent)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}