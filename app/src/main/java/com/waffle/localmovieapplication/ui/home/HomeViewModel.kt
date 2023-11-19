package com.waffle.localmovieapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.waffle.core.base.Resource
import com.waffle.core.data.usecase.PopularUseCase
import com.waffle.core.local.model.Popular
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: PopularUseCase) : ViewModel() {
    fun getPopularList(): LiveData<Resource<List<Popular>>> {
       return useCase.getAllPopular().asLiveData()
    }

    fun updatePopular(popular : Popular) {
        viewModelScope.launch {
           useCase.updatePopularFavorite(popular)
        }
    }
}