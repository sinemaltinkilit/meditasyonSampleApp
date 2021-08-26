package com.example.meditasyonapp.feature.meditationdetail.presentation

import android.text.format.DateFormat
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.meditasyonapp.base.model.DetailItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MeditationDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val DATE_PATTERN = "MM/dd/yyyy, EEE"
    }

    private val backgroundImage = MutableLiveData<String>()
    val backgroundImageLiveData: LiveData<String> get() = backgroundImage

    var mediaTitleObservable = ObservableField<String>()
    var mediaDetailTextObservable = ObservableField<String>()
    var mediaDateObservable = ObservableField<String>()

    private val detailItem = savedStateHandle.get<DetailItemModel>("detailItem")

    fun setUIData() {
        detailItem?.run {
            mediaTitleObservable.set(title)
            mediaDetailTextObservable.set(detailText)
            mediaDateObservable.set(getDateText(date))
            backgroundImage.value = imageUrl
        }
    }

    /**
     * Formatli date i dönerwer
     */
    private fun getDateText(date: String?) = date?.takeIf { it.isNotEmpty() }?.let {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = it.toLong() * 1000L
        DateFormat.format(DATE_PATTERN, calendar).toString()
    }
}