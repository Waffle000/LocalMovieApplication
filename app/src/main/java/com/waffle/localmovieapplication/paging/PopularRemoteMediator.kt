package com.waffle.localmovieapplication.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.waffle.localmovieapplication.api.ApiService
import com.waffle.localmovieapplication.local.LocalDatabase
import com.waffle.localmovieapplication.local.entity.KeyEntity
import com.waffle.localmovieapplication.local.entity.PopularEntity
import okio.IOException
import retrofit2.HttpException

@ExperimentalPagingApi
class PopularRemoteMediator(
    private val api: ApiService,
    private val db: LocalDatabase
) : RemoteMediator<Int, PopularEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularEntity>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = api.getPopularList(page = page)
            val isEndOfList = response.body()?.results?.isEmpty() ?: false
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.popularDao().deleteAll()
                    db.keyDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val data = response.body()?.results?.map {
                    PopularEntity(id = it?.id ?: 0,
                        name = it?.originalTitle,
                        date = it?.releaseDate,
                        popularity = it?.popularity,
                        star = it?.voteAverage,
                        posterPath = it?.posterPath,
                        backdropPath = it?.backdropPath,
                        overview = it?.overview)
                }
                val keys = response.body()?.results?.map {
                    KeyEntity(id = it?.id ?: 0, prevKey = prevKey, nextKey = nextKey)
                }
                db.popularDao().insertPopularList(data ?: listOf())
                db.keyDao().insertKeyList(keys ?: listOf())
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, PopularEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PopularEntity>): KeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                db.keyDao().getKeyId(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, PopularEntity>): KeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { data -> db.keyDao().getKeyId(data.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, PopularEntity>): KeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { data -> db.keyDao().getKeyId(data.id) }
    }
}