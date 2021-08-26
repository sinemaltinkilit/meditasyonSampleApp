package com.example.meditasyonapp.feature.home.data

import com.example.meditasyonapp.feature.home.data.response.HomeResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("files/MobileInterviewProjectData.json")
    suspend fun getHomeData() : HomeResponse
}