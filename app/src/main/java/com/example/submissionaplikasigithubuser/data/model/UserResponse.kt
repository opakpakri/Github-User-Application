package com.example.submissionaplikasigithubuser.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("total_count")
    val dataCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @SerializedName("items")
    val dataUsers: List<SearchData>,
)

data class SearchData(
    @SerializedName("id")
    val id: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("type")
    val user_type: String,

)