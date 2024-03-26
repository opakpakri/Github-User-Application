package com.example.submissionaplikasigithubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionaplikasigithubuser.api.ApiConfig
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import com.example.submissionaplikasigithubuser.data.model.DetailUserResponse
import com.example.submissionaplikasigithubuser.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val detailUsers = MutableLiveData<DetailUserResponse>()
    val getDetailUsers: LiveData<DetailUserResponse> = detailUsers

    private val loadingBar = MutableLiveData<Boolean>()
    val getLoadingBar: LiveData<Boolean> = loadingBar

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun setDetailUsers(username: String){
        loadingBar.value = true
        ApiConfig.getApiService().getDetailUsers(username).enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                loadingBar.value = false
                if (response.isSuccessful) {
                    detailUsers.value = response.body()
                }

            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                loadingBar.value = false
                t.message?.let { println("Failure: $it") }
            }

        })
    }

    fun insert(favorite: FavoriteUser) {
        mFavoriteRepository.insert(favorite)
    }
    fun update(favorite: FavoriteUser) {
        mFavoriteRepository.update(favorite)
    }
    fun delete(favorite: FavoriteUser) {
        mFavoriteRepository.delete(favorite)
    }
    fun isUserFavorite(username: String): LiveData<Boolean> {
        return mFavoriteRepository.isUserFavorite(username)
    }

}
