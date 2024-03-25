package com.example.submissionaplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubuser.data.model.SearchData
import com.example.submissionaplikasigithubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser = ArrayList<SearchData>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(data: ArrayList<SearchData>) {
        val diffCallback = DiffUtilCallback(listUser, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUser.clear()
        listUser.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: SearchData) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .skipMemoryCache(true)
                    .into(imgAvatarUser)
                tvNameUser.text = user.login
                tvUserType.text = user.user_type
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SearchData)
    }

    class DiffUtilCallback(private val oldList: List<SearchData>, private val newList: List<SearchData>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}
