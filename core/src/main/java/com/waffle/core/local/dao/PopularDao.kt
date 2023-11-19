package com.waffle.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.waffle.core.local.entity.PopularEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPopularList(list : List<PopularEntity>)

    @Query("SELECT * FROM popular_db")
    fun getPopularList() : Flow<List<PopularEntity>>

    @Query("SELECT * FROM popular_db WHERE is_favorite == 1")
    fun getPopularFavorite() : Flow<List<PopularEntity>>

    @Update
    suspend fun updatePopular(popular: PopularEntity)
    @Query("DELETE FROM popular_db")
    suspend fun deleteAll()
}