package com.example.fruitdetectionnews.viewmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostModel (
    val id: String? = null,
    val author: String? = null,
    val NewsDate: String? = null,
    val title: String? = null,
    val NewsLink: String? = null,
    val source: String? = null,
    val ShortDesc: String? = null,
    val NewsImage: String? = null
):Parcelable

