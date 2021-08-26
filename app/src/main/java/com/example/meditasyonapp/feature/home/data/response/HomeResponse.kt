package com.example.meditasyonapp.feature.home.data.response

import com.example.meditasyonapp.base.model.MeditationItemModel
import com.example.meditasyonapp.base.model.StoriesItemModel

data class HomeResponse(
    val isBannerEnabled: Boolean? = null,

    val meditations: List<MeditationItemModel>? = null,

    val stories: List<StoriesItemModel>? = null
)
