package com.example.submissionaplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionaplikasigithubuser.data.model.FollowResponse
import com.example.submissionaplikasigithubuser.databinding.ItemUserBinding

class FollowUserAdapter : RecyclerView.Adapter<FollowUserAdapter.ListViewHolder>() {
    private val followUsers = ArrayList<FollowResponse>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(data: ArrayList<FollowResponse>) {
        val diffCallback = DiffUtilCallback(followUsers, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        followUsers.clear()
        followUsers.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = followUsers[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                followUsers[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return followUsers.size
    }

    class ListViewHolder(private val bindings: ItemUserBinding) :
        RecyclerView.ViewHolder(bindings.root) {
        fun bind(user: FollowResponse) {
            bindings.tvNameUser.text = user.login
            bindings.tvUserType.text = user.user_type

            Glide.with(itemView.context)
                .load(user.avatar_url)
                .skipMemoryCache(true)
                .into(bindings.imgAvatarUser)
        }
    }

    fun interface OnItemClickCallback {
        fun onItemClicked(data: FollowResponse)
    }

    class DiffUtilCallback(
        private val oldList: List<FollowResponse>,
        private val newList: List<FollowResponse>
    ) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.javaClass == newItem.javaClass
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.hashCode() == newItem.hashCode()
        }

        @Override
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}