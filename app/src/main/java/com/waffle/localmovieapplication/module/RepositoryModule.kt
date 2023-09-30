package com.waffle.localmovieapplication.module

import androidx.paging.ExperimentalPagingApi
import com.waffle.localmovieapplication.data.AppRepository
import org.koin.dsl.module

@ExperimentalPagingApi
val repositoryModule = module {
    single {
        AppRepository(get(), get())
    }
}