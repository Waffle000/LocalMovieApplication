package com.waffle.localmovieapplication.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.waffle.localmovieapplication.local.entity.KeyEntity

@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeyList(list : List<KeyEntity>)

    @Query("SELECT * FROM key_db WHERE id =:id")
    suspend fun getKeyId(id: Int) : KeyEntity?

    @Query("DELETE FROM key_db")
    suspend fun deleteAll()
}