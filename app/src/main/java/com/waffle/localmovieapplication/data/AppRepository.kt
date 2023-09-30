package com.waffle.localmovieapplication.data

import com.waffle.localmovieapplication.api.ApiService
import com.waffle.localmovieapplication.data.response.PopularResponse
import com.waffle.localmovieapplication.local.LocalDatabase
import com.waffle.localmovieapplication.local.entity.PopularEntity
import retrofit2.Response

class AppRepository(private val apiService: ApiService, private val localDatabase: LocalDatabase) {

    suspend fun insertPopularList(data : List<PopularEntity>) {
        localDatabase.popularDao().insertPopularList(data)
    }

    suspend fun getPopularLocal() : List<PopularEntity> {
        return localDatabase.popularDao().getPopularList()
    }

    suspend fun deletePopularLocal() {
        localDatabase.popularDao().deleteAll()
    }

    suspend fun getPopular(page: Int) : Response<PopularResponse> {
        return apiService.getPopularList(page)
    }
}
