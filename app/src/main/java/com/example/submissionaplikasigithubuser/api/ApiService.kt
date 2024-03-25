package com.example.submissionaplikasigithubuser.api

import com.example.submissionaplikasigithubuser.data.model.DetailUserResponse
import com.example.submissionaplikasigithubuser.data.model.FollowResponse
import com.example.submissionaplikasigithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowerUsers(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponse>>

    @GET("users/{username}/following")
    fun getFollowingUsers(
        @Path("username") username: String
    ): Call<ArrayList<FollowResponse>>
}
