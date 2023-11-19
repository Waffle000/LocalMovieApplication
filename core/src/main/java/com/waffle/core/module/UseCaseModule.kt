package com.waffle.core.module

import com.waffle.core.data.AppRepository
import com.waffle.core.data.usecase.PopularUseCase
import org.koin.dsl.module

val useCaseModule = module {
   factory { PopularUseCase(get()) }
}