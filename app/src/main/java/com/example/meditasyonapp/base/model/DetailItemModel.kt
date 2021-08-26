package com.example.meditasyonapp.base.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DetailItemModel(
    var date: String? = null,
    var detailText: String? = null,
    var title: String? = null,
    var imageUrl: String? = null
) : Parcelable