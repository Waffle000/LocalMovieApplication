package com.waffle.localmovieapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.waffle.localmovieapplication.data.AppRepository
import com.waffle.localmovieapplication.local.entity.PopularEntity
import com.waffle.localmovieapplication.utils.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : ViewModel() {

    private val getPopularSuccess = MutableLiveData<SingleLiveEvent<Pair<List<PopularEntity>, Boolean>>>()
    fun observeGetPopularSuccess(): LiveData<SingleLiveEvent<Pair<List<PopularEntity>, Boolean>>> =
        getPopularSuccess

    private val getPopularRemoteSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeGetPopularRemoteSuccess(): LiveData<SingleLiveEvent<Boolean>> =
        getPopularRemoteSuccess

    private val isError = MutableLiveData<SingleLiveEvent<String>>()
    fun observeIsError(): LiveData<SingleLiveEvent<String>> = isError

    fun getPopularList(page: Int, parameter: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.getPopular(page).body()
                if (result != null) {
                    repository.deletePopularLocal()
                    val data = result.results?.map {
                        PopularEntity(
                            id = it?.id ?: 0,
                            name = it?.originalTitle,
                            date = it?.releaseDate,
                            popularity = it?.popularity,
                            star = it?.voteAverage,
                            posterPath = it?.posterPath,
                            backdropPath = it?.backdropPath,
                            overview = it?.overview
                        )
                    }
                    data?.let { repository.insertPopularList(it) }
                    getPopularRemoteSuccess.postValue(SingleLiveEvent(true))
                    getPopularListLocal(parameter)

                } else {
                    isError.postValue(SingleLiveEvent("Data Kosong"))
                }
            } catch (e: Exception) {
                isError.postValue(SingleLiveEvent(e.message ?: "Terjadi Kesalahan"))
            }
        }
    }

    fun getPopularListLocal(parameter: Boolean) {
        viewModelScope.launch {
            try {
                val result = repository.getPopularLocal()
                getPopularSuccess.postValue(SingleLiveEvent(Pair(result, parameter)))
            } catch (e: Exception) {
                isError.postValue(SingleLiveEvent(e.message ?: "Terjadi Kesalahan"))
            }
        }
    }
}