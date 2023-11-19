package com.waffle.core.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "popular_db")
data class PopularEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "date")
    val date: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Float?,
    @ColumnInfo(name = "star")
    val star: Float?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
) : Parcelable
