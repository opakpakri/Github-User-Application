package com.example.submissionaplikasigithubuser.ui.main

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionaplikasigithubuser.R
import com.example.submissionaplikasigithubuser.adapter.UserAdapter
import com.example.submissionaplikasigithubuser.data.model.SearchData
import com.example.submissionaplikasigithubuser.databinding.ActivityMainBinding
import com.example.submissionaplikasigithubuser.viewmodel.MainViewModel
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userAdapter = UserAdapter()
    private val viewMainModel: MainViewModel by viewModels()
    private lateinit var sharedPrefs: SharedPreferences

    private var isLoadingData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE)
        val isDarkModeActive = sharedPrefs.getBoolean("isDarkModeActive", false)
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.show()
        setContentView(binding.root)
        setupRecyclerView()
        observeViewModels()
    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (!isLoadingData && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    isLoadingData = false
                }
            }
        })

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: SearchData) {
                selectedSearchUserData(user)
            }
        })
    }

    private fun selectedSearchUserData(user: SearchData) {
        startActivityWithExtra(UserDetailActivity::class.java, UserDetailActivity.EXTRA_USER, user.login)
    }

    private fun observeViewModels() {
        viewMainModel.getSearchUsers().observe(this) { searchList ->
            handleSearchListChange(searchList)
        }
        viewMainModel.getLoadingBar.observe(this) { isLoading ->
            handleProgressBarVisibility(isLoading)
        }

    }

    private fun handleSearchListChange(searchList: List<SearchData>) {
        if (searchList.isNotEmpty()) {
            handleVisibility(binding.tvNotFound, View.GONE)
            handleVisibility(binding.rvUser, View.VISIBLE)
            handleVisibility(binding.tvTextContent, View.GONE)
            userAdapter.setData(ArrayList(searchList))
            binding.rvUser.adapter = userAdapter
        } else {
            handleVisibility(binding.tvNotFound, View.VISIBLE)
            handleVisibility(binding.rvUser, View.GONE)
            handleVisibility(binding.tvTextContent, View.GONE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewMainModel.setSearchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_setting -> {
                openSettings()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettings() {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
    }

    private fun handleVisibility(view: View, visibility: Int) {
        view.visibility = visibility
    }

    private fun startActivityWithExtra(activityClass: Class<*>, extraKey: String, extraValue: String) {
        val intent = Intent(this, activityClass)
        intent.putExtra(extraKey, extraValue)
        startActivity(intent)
    }

    private fun handleProgressBarVisibility(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
