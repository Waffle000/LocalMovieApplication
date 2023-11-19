package com.waffle.core.data.repository

import com.waffle.core.base.Resource
import com.waffle.core.local.entity.PopularEntity
import com.waffle.core.local.model.Popular
import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    fun getAllPopular(): Flow<Resource<List<Popular>>>
    fun getPopularFavorite() : Flow<Resource<List<Popular>>>
    suspend fun updatePopularFavorite(popular: Popular)
}