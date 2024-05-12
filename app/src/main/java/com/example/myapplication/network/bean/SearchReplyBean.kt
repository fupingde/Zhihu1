package com.example.myapplication.network.bean


import android.os.Parcelable
import java.io.Serializable


data class SearchReplyBean(
    val date: String,
    val stories: List<Story>,
    val top_stories: List<TopStory>
): Serializable

data class Story(
    val hint: String,
    val id: Int,
    val images: List<String>,
    val title: String,
    val url: String
): Serializable

data class TopStory(
    val hint: String,
    val id: Int,
    val image: String,
    val title: String,
    val url: String
): Serializable
