package com.example.meditasyonapp.feature.home.domain

import com.example.meditasyonapp.base.model.StoriesItemModel

interface StoryItemClickListener {

    fun onStoryItemSelected(selectedStory: StoriesItemModel)
}