package com.waffle.core.data.usecase

import com.waffle.core.base.Resource
import com.waffle.core.data.repository.PopularRepository
import com.waffle.core.local.model.Popular
import kotlinx.coroutines.flow.Flow

class PopularUseCase(private val popularRepository: PopularRepository) {

    fun getAllPopular() : Flow<Resource<List<Popular>>> {
        return popularRepository.getAllPopular()
    }

    fun getPopularFavorite() : Flow<Resource<List<Popular>>> {
        return popularRepository.getPopularFavorite()
    }

    suspend fun updatePopularFavorite(popular: Popular) {
        popularRepository.updatePopularFavorite(popular)
    }
}