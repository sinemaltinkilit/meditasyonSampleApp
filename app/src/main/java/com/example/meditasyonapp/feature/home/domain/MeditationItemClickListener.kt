package com.example.meditasyonapp.feature.home.domain

import com.example.meditasyonapp.base.model.MeditationItemModel

interface MeditationItemClickListener {
    fun onMeditationItemSelected(selectedMeditation: MeditationItemModel)
}