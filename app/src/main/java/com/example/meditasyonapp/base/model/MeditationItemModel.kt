package com.example.meditasyonapp.base.model

data class MeditationItemModel(
    val title: String?,
    val subtitle: String?,
    val image: ImageModel?,
    val releaseDate: String?,
    val content: String?
)

data class ImageModel(
    val small: String?,
    val large: String?
)
