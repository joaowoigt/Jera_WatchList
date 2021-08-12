package com.woigt.jerawatchlist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


class Perfil(
    var name: String,
    var listmovies: List<Movie> = listOf()


 ){
    constructor() : this("", emptyList<Movie>())
}




