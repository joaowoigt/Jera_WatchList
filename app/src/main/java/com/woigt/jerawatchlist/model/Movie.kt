package com.woigt.jerawatchlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    val isWatched: Boolean = false
){
    constructor() : this("", "")
}

