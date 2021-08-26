package com.example.meditasyonapp.feature.home.presentation

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.meditasyonapp.base.model.MeditationItemModel
import com.example.meditasyonapp.base.model.StoriesItemModel
import com.example.meditasyonapp.feature.home.data.repo.HomeDataRepository
import com.example.meditasyonapp.feature.home.data.response.HomeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeDataRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val meditationListToAdapter = MutableLiveData<List<MeditationItemModel>?>()
    val meditationListToAdapterLiveData: LiveData<List<MeditationItemModel>?> get() = meditationListToAdapter

    private val storyListToAdapter = MutableLiveData<List<StoriesItemModel>?>()
    val storyListToAdapterLiveData: LiveData<List<StoriesItemModel>?> get() = storyListToAdapter

    var bannerVisibilityObservable = ObservableField(View.GONE)
    var progressBarVisibilityObservable = ObservableField(View.GONE)
    var meditationListVisibilityObservable = ObservableField(View.GONE)
    var storyListVisibilityObservable = ObservableField(View.GONE)

    fun getHomeData() {
        progressBarVisibilityObservable.set(View.VISIBLE)
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getHomeScreenData()
            handleResponse(response)
            progressBarVisibilityObservable.set(View.GONE)
        }
    }

    private fun handleResponse(response: HomeResponse) {
        response.run {
            isBannerEnabled?.takeIf { it }?.let { bannerVisibilityObservable.set(View.VISIBLE) }
            meditations?.takeIf { !it.isNullOrEmpty() }?.let {
                meditationListVisibilityObservable.set(View.VISIBLE)
                meditationListToAdapter.postValue(meditations)
            }
            stories?.takeIf { !it.isNullOrEmpty() }?.let {
                storyListVisibilityObservable.set(View.VISIBLE)
                storyListToAdapter.postValue(stories)
            }
        }
    }
}