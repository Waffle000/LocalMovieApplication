package com.waffle.core.module

import com.waffle.core.data.AppRepository
import com.waffle.core.data.RemoteDataSource
import com.waffle.core.data.repository.PopularRepository
import com.waffle.core.local.LocalDataSource
import org.koin.dsl.module

val repositoryModule = module {
    single<PopularRepository> {
        AppRepository(get(), get())
    }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get()) }
}