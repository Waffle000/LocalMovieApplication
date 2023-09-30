package com.waffle.localmovieapplication.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "key_db")
class KeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")
    val id: Int,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?,
) : Parcelable