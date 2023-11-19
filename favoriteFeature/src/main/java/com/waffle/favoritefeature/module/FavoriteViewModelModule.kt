package com.waffle.favoritefeature.module

import com.waffle.favoritefeature.FavoriteViewModel
import org.koin.dsl.module

val favoriteViewModelModule = module {
    single { FavoriteViewModel(get()) }
}