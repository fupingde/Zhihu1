package com.example.myapplication.network.bean

import java.io.Serializable

data class DataModel(
    val images: List<String>,
    val title: String,
    val hint: String,
    val url: String
) : Serializable
