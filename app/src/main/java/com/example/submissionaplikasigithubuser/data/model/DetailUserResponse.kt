package com.example.submissionaplikasigithubuser.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("name")
    val user_name: String,

    @SerializedName("followers")
    val user_follower: String,

    @SerializedName("following")
    val user_following: String,

    @SerializedName("company")
    val user_company: String,

    @SerializedName("location")
    val user_location: String,

    @SerializedName("public_repos")
    val user_repository: String,

    @SerializedName("type")
    val user_type: String,

)
