package com.example.submissionaplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import com.example.submissionaplikasigithubuser.repository.FavoriteRepository

class FavoriteViewModel (application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getallFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getallFavoriteUsers()
}