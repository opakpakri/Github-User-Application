package com.example.submissionaplikasigithubuser.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubuser.R
import com.example.submissionaplikasigithubuser.adapter.ViewPagerAdapter
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import com.example.submissionaplikasigithubuser.databinding.ActivityUserDetailBinding
import com.example.submissionaplikasigithubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModelDetailUser: DetailViewModel by viewModels()
    private var isLoading = false
    private var imgUrl: String = ""
    private var typeuser: String = ""
    private var favoriteUser: FavoriteUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        observeViewModel()
        username = intent.getStringExtra(EXTRA_USER).toString()
        username?.let { showViewModel(it) }

        supportActionBar?.title = username

        binding.tvDetailUsername.setOnClickListener {
            copyUserUrlToClipboard()
        }

        viewModelDetailUser.isUserFavorite(username).observe(this) { isFavorite ->
            if (isFavorite) {
                binding?.fabAdd?.setImageResource(R.drawable.ic_favorite)
                binding?.fabAdd?.setOnClickListener {
                    Toast.makeText(this, "Unfavorited", Toast.LENGTH_SHORT).show()
                    deleteFavoriteUser()
                }
            } else {
                binding?.fabAdd?.setImageResource(R.drawable.ic_favorite_border)
                binding?.fabAdd?.setOnClickListener {
                    Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show()
                    addFavoritesUser()
                }
            }
        }
    }

    private fun addFavoritesUser() {
        username?.let {
            val favoriteUser = FavoriteUser(it)
            favoriteUser.profilePhoto = imgUrl
            favoriteUser.user_type = typeuser

            viewModelDetailUser.insert(favoriteUser)
        }
    }

    private fun deleteFavoriteUser() {
        username?.let {
            val favoriteUser = FavoriteUser(it)
            viewModelDetailUser.delete(favoriteUser)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("$username")
        //supportActionBar?.setDisplayShowTitleEnabled(false)
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

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPagerDetail.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPagerDetail) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.user_followers)
                1 -> getString(R.string.user_following)
                else -> ""
            }
        }.attach()
    }

    private fun showViewModel(username: String) {
        viewModelDetailUser.setDetailUsers(username)
        viewModelDetailUser.getDetailUsers.observe(this) { detailUser ->
            Glide.with(this)
                .load(detailUser.avatar_url)
                .skipMemoryCache(true)
                .into(binding.imgAvatar)

            binding.tvDetailUsername.text = detailUser.login ?: "-"
            binding.tvDetailName.text = detailUser.user_name ?: "-"
            binding.tvDetailCompany.text = detailUser.user_company ?: "-"
            binding.tvDetailLocation.text = detailUser.user_location ?: "-"
            binding.tvDetailRepositories.text = detailUser.user_repository ?: "-"
            binding.tvDetailFollowers.text = detailUser.user_follower ?: "-"
            binding.tvDetailFollowings.text = detailUser.user_following ?: "-"

            favoriteUser?.login= detailUser.login
            typeuser = detailUser.user_type
            imgUrl = detailUser.avatar_url
        }
    }

    private fun observeViewModel() {
        viewModelDetailUser.getLoadingBar.observe(this, this::showLoading)
    }

    private fun showLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun copyUserUrlToClipboard() {
        val githubUrl = "https://github.com/$username"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
        startActivity(intent)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        var username = String()
    }
}