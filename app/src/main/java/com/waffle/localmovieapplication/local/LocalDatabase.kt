package com.waffle.localmovieapplication.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waffle.localmovieapplication.local.dao.KeyDao
import com.waffle.localmovieapplication.local.dao.PopularDao
import com.waffle.localmovieapplication.local.entity.KeyEntity
import com.waffle.localmovieapplication.local.entity.PopularEntity

@Database(
    entities = [
        KeyEntity::class,
        PopularEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun keyDao() : KeyDao
    abstract fun popularDao() : PopularDao
}