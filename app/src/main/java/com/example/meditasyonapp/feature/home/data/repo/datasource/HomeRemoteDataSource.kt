package com.example.meditasyonapp.feature.home.data.repo.datasource

import com.example.meditasyonapp.feature.home.data.HomeApiService
import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val service: HomeApiService
) {
    suspend fun getHomeData() = service.getHomeData()
}