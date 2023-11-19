package com.waffle.localmovieapplication.ui.module

import com.waffle.localmovieapplication.ui.home.HomeViewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    single { HomeViewModel(get()) }
}