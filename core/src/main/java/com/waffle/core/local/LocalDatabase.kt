package com.waffle.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waffle.core.local.dao.PopularDao
import com.waffle.core.local.entity.PopularEntity

@Database(
    entities = [
        PopularEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun popularDao(): PopularDao
}