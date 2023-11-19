package com.waffle.core.data

import android.provider.ContactsContract.Data
import android.util.Log
import com.waffle.core.base.ApiResponse
import com.waffle.core.base.Resource
import com.waffle.core.data.repository.PopularRepository
import com.waffle.core.data.response.ResultsItemPopular
import com.waffle.core.local.LocalDataSource
import com.waffle.core.local.entity.PopularEntity
import com.waffle.core.local.model.Popular
import com.waffle.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AppRepository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) : PopularRepository {

    override fun getAllPopular(): Flow<Resource<List<Popular>>> =
        object : NetworkBoundResource<List<Popular>, List<ResultsItemPopular?>>() {
            override fun loadFromDB(): Flow<List<Popular>> {
                return localDataSource.getLocalPopular().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItemPopular?>>> {
               return remoteDataSource.getAllPopular()
            }

            override suspend fun saveCallResult(data: List<ResultsItemPopular?>) {
                val list = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPopular(list)
            }

            override fun shouldFetch(data: List<Popular>?): Boolean =
                data.isNullOrEmpty()


        }.asFlow()
    override fun getPopularFavorite() : Flow<Resource<List<Popular>>> {
        return localDataSource.getPopularFavorite().map {
            Resource.Loading(null)
            try {
                val response = it
                if (response.isNotEmpty()){
                    Resource.Success(DataMapper.mapEntitiesToDomain(response))
                } else {
                    Resource.Error("Empty Data")
                }
            } catch (e : Exception){
                Resource.Error(e.toString())
            }
        }
    }

    override suspend fun updatePopularFavorite(popular: Popular) {
        localDataSource.updatePopular(DataMapper.mapDomainToEntity(popular))
    }


}
