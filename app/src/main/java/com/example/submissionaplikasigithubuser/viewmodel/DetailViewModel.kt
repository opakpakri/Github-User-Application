package com.example.submissionaplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasigithubuser.api.ApiConfig
import com.example.submissionaplikasigithubuser.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val detailUsers = MutableLiveData<DetailUserResponse>()
    val getDetailUsers: LiveData<DetailUserResponse> = detailUsers

    private val loadingBar = MutableLiveData<Boolean>()
    val getLoadingBar: LiveData<Boolean> = loadingBar

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
                t.message?.let { Log.d("Failure", it) }
            }

        })
    }

}