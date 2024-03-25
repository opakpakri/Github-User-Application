package com.example.submissionaplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasigithubuser.api.ApiConfig
import com.example.submissionaplikasigithubuser.data.model.FollowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val followerUsers = MutableLiveData<ArrayList<FollowResponse>>()
    val getFollowers: LiveData<ArrayList<FollowResponse>> = followerUsers

    private val loadingBar = MutableLiveData<Boolean>()
    val getLoadingBar: LiveData<Boolean> = loadingBar

    fun setFollowerUsers(username: String){
        loadingBar.value = true
        ApiConfig.getApiService().getFollowerUsers(username).enqueue(object : Callback<ArrayList<FollowResponse>>{
            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                loadingBar.value = false
                if (response.isSuccessful) {
                    followerUsers.value = response.body()
                }

            }
            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                loadingBar.value = false
                t.message?.let { Log.d("Failure", it) }
            }
        })
    }

}