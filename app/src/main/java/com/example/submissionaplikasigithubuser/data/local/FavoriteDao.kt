package com.example.submissionaplikasigithubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(Favorite: FavoriteUser)
    @Update
    fun update(Favorite: FavoriteUser)
    @Delete
    fun delete(Favorite: FavoriteUser)
    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getallFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteUser WHERE username = :username)")
    fun isUserFavorite(username: String): LiveData<Boolean>
}