package com.sideproject.githupapidemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sideproject.githupapidemo.databinding.ItemUserBinding
import com.sideproject.githupapidemo.model.User

class UserPageAdapter : PagingDataAdapter<User, UserPageAdapter.ViewHolder>(diffCallback) {

    private var onItemClickLister: ((User?, Int) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User?, position: Int) {
            itemView.apply {
                Glide.with(this).load(item?.avatar_url).into(binding.imgUser)
                binding.tvUserName.text = item?.login
                binding.tvUserType.text = item?.type
                setOnClickListener {
                    onItemClickLister?.let {
                        it(item, position)
                    }
                }
            }
        }
    }

    fun setOnItemClickLister(lister: (User?, Int) -> Unit) {
        onItemClickLister = lister
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val listItemBinding = ItemUserBinding.inflate(view, parent, false)
        return ViewHolder(listItemBinding)
    }

}