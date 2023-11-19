package com.waffle.core.data

import android.util.Log
import com.waffle.core.api.ApiService
import com.waffle.core.base.ApiResponse
import com.waffle.core.data.response.ResultsItemPopular
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllPopular(): Flow<ApiResponse<List<ResultsItemPopular?>>> {
        return flow {
            try {
                val response = apiService.getPopularList().body()
                val dataArray = response?.results ?: listOf()
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}