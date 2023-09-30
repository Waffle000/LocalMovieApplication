package com.waffle.localmovieapplication.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waffle.localmovieapplication.local.entity.PopularEntity

@Dao
interface PopularDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularList(list : List<PopularEntity>)

    @Query("SELECT * FROM popular_db")
    suspend fun getPopularList() : List<PopularEntity>

    @Query("DELETE FROM popular_db")
    suspend fun deleteAll()
}