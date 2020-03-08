package com.yulmaso.vkauth.ui.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yulmaso.vkauth.databinding.ItemFriendBinding
import com.yulmaso.vkauth.data.vk.VKUser
import kotlin.random.Random

class FriendListAdapter: RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    private var users: List<VKUser> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemFriendBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setUsers(users: List<VKUser>){
        this.users = users
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemFriendBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(item: VKUser){
            binding.apply {
                name = item.firstName + " " + item.lastName
                image = item.photo
            }
        }

    }
}