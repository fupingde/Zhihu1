package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.network.bean.Story

class RVAdapter : ListAdapter<Story, RVAdapter.StoryViewHolder>(StoryDiffCallback()) {

    var onItemClick: ((Int) -> Unit)? = null

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvHint: TextView = itemView.findViewById(R.id.tv_hint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        Glide.with(holder.itemView.context).load(story.images.firstOrNull()).centerCrop().into(holder.imageView)
        holder.tvTitle.text = story.title
        holder.tvHint.text = story.hint

        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(position)
        }
    }


    class StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }
    }
}
