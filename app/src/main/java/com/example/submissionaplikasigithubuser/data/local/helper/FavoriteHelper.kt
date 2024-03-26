package com.example.submissionaplikasigithubuser.data.local.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser

class FavoriteHelper (private val oldFavList: List<FavoriteUser>, private val newFavList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].login == newFavList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.login == newFav.login && oldFav.user_type == newFav.user_type && oldFav.profilePhoto == newFav.profilePhoto
    }
}