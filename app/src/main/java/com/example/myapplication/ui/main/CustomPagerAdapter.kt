package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.network.bean.DataModel

class CustomPagerAdapter(private val dataList: List<DataModel>) :
    RecyclerView.Adapter<CustomPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageviewtop)
        val tvTitle: TextView = itemView.findViewById(R.id.textView1)
        val tvHint: TextView = itemView.findViewById(R.id.textView2)
        val tvUrl: WebView = itemView.findViewById(R.id.webview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        Glide.with(holder.itemView.context).load(item.images.firstOrNull()).into(holder.imageView)
        holder.tvTitle.text = item.title
        holder.tvHint.text = item.hint


        holder.tvUrl.settings.javaScriptEnabled = true
        holder.tvUrl.webViewClient = WebViewClient()
        holder.tvUrl.loadUrl(item.url)
    }

    override fun getItemCount(): Int = dataList.size
}



