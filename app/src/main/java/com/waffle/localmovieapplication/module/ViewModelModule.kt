package com.waffle.localmovieapplication.module

import androidx.paging.ExperimentalPagingApi
import com.waffle.localmovieapplication.ui.MainViewModel
import org.koin.dsl.module


@ExperimentalPagingApi
val viewModelModule = module {
    single { MainViewModel(get()) }
}