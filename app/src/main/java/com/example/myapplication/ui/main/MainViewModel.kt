package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.network.bean.Story
import com.example.myapplication.network.bean.TopStory

class MainViewModel : ViewModel() {
    private val _storyList = MutableLiveData<List<Story>>()
    val storyList: LiveData<List<Story>> = _storyList

    private val _topStoryList = MutableLiveData<List<TopStory>>()
    val topStoryList: LiveData<List<TopStory>> = _topStoryList

    fun updateStoryList(stories: List<Story>, topStories: List<TopStory>) {
        _storyList.value = stories
        _topStoryList.value = topStories
    }
}
