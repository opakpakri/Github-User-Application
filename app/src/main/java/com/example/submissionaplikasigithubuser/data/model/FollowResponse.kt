package com.example.submissionaplikasigithubuser.data.model

import com.google.gson.annotations.SerializedName

data class FollowResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("type")
    val user_type: String

)
