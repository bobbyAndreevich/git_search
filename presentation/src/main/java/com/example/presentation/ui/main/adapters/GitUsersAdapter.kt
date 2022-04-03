package com.example.presentation.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.User
import com.example.presentation.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_git_user.view.*

class GitUsersAdapter(context: Context):
    PagingDataAdapter<User, GitUsersViewHolder>(GitUserDiffItemCallback) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: GitUsersViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitUsersViewHolder {
        return GitUsersViewHolder(layoutInflater.inflate(R.layout.item_git_user, parent, false))
    }
}

class GitUsersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(user: User?) {
        val placeholder = itemView.context.getDrawable(R.mipmap.ic_github_menu)!!
        Picasso.get().load(user?.avatarUrl).resize(placeholder.intrinsicWidth, placeholder.intrinsicHeight)
            .placeholder(placeholder).into(itemView.git_user_image)
        itemView.git_login.text = user?.login
    }
}

private object GitUserDiffItemCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.url == oldItem.url
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.avatarUrl == newItem.avatarUrl && oldItem.login == newItem.login
    }
}