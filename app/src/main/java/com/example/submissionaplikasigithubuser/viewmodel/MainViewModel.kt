package com.example.submissionaplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionaplikasigithubuser.api.ApiConfig
import com.example.submissionaplikasigithubuser.data.model.SearchData
import com.example.submissionaplikasigithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<SearchData>>()

    private val loadingBar = MutableLiveData<Boolean>()
    val getLoadingBar: LiveData<Boolean> = loadingBar

    fun setSearchUsers(query: String){
        loadingBar.value = true
        ApiConfig.getApiService().getSearchUsers(query).enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    loadingBar.value = false
                    if (response.isSuccessful) {
                        val userList = ArrayList(response.body()?.dataUsers ?: emptyList())
                        listUsers.postValue(userList)
                    }

                }
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    loadingBar.value = false
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<SearchData>>{
        return listUsers
    }
}