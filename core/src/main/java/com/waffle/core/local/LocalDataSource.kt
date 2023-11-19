package com.waffle.core.local

import com.waffle.core.local.entity.PopularEntity
import com.waffle.core.local.model.Popular
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val localDatabase: LocalDatabase) {

    fun getLocalPopular(): Flow<List<PopularEntity>> {
        return localDatabase.popularDao().getPopularList()
    }

    fun getPopularFavorite(): Flow<List<PopularEntity>> {
        return localDatabase.popularDao().getPopularFavorite()
    }

    suspend fun insertPopular(input: List<PopularEntity>) =
        localDatabase.popularDao().insertPopularList(input)

    suspend fun updatePopular(input: PopularEntity) = localDatabase.popularDao().updatePopular(input)

}