package com.example.submissionaplikasigithubuser.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteUser")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var login: String = "",

    @ColumnInfo(name = "type")
    var user_type: String? = null,

    @ColumnInfo(name = "profilePhoto")
    var profilePhoto: String? = null
)
