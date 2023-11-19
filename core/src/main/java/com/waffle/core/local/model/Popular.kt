package com.waffle.core.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    val id: Int,
    val name: String?,
    val date: String?,
    val popularity: Float?,
    val star: Float?,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String?,
    var isFavorite: Boolean
) : Parcelable
