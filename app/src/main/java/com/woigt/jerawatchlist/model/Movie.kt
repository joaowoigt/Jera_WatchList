package com.woigt.jerawatchlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("genre_ids") val genre_ids: List<Int>,

){
    constructor() : this("", "", emptyList<Int>())
}
