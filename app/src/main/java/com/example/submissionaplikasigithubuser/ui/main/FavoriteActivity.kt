package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val settingsItem = menu.findItem(R.id.menu_setting)
        menu.findItem(R.id.menu_search)?.isVisible = false
        settingsItem.setOnMenuItemClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
            } else {
                binding?.tvEmpty?.visibility = View.VISIBLE
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