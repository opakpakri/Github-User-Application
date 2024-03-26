package com.example.submissionaplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubuser.data.local.FavoriteUser
import com.example.submissionaplikasigithubuser.databinding.ItemUserBinding

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.ListViewHolder>(){

    private val listUserData = ArrayList<FavoriteUser>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserAdapter.ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserAdapter.ListViewHolder, position: Int) {
        val user = listUserData[position]
        holder.bind(user)
        onItemClickCallback?.let { callback ->
            holder.itemView.setOnClickListener {
                callback.onItemClicked(listUserData[position])
            }
        }
    }

    override fun getItemCount(): Int = listUserData.size

    fun setListData(data: List<FavoriteUser>) {
        val diffCallback = DiffUtilCallback(listUserData, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listUserData.clear()
        listUserData.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteUser)
    }

    class ListViewHolder(private val _binding: ItemUserBinding) : RecyclerView.ViewHolder(_binding.root) {
        fun bind(user: FavoriteUser) {
            _binding.tvNameUser.text = user.login
            _binding.tvUserType.text = user.user_type
            Glide.with(itemView.context)
                .load(user.profilePhoto)
                .skipMemoryCache(true)
                .into(_binding.imgAvatarUser)
        }
    }

    class DiffUtilCallback(private val oldList: List<FavoriteUser>, private val newList: List<FavoriteUser>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}