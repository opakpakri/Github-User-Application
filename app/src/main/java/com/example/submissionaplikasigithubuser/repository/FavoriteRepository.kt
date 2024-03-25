package com.example.submissionaplikasigithubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionaplikasigithubuser.data.local.FavoriteDao
import com.example.submissionaplikasigithubuser.data.local.FavoriteRoomDatabase
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val database = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = database.favoriteDao()
    }
    fun getallFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteDao.getallFavoriteUsers()
    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favoriteUser) }
    }
    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(favoriteUser) }
    }
    fun update(favoriteUser: FavoriteUser) {
        executorService.execute { mFavoriteDao.update(favoriteUser) }
    }
    fun isUserFavorite(username: String): LiveData<Boolean> {
        return mFavoriteDao.isUserFavorite(username)
    }
}