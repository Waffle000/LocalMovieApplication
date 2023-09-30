package com.waffle.localmovieapplication.module

import com.waffle.localmovieapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN_KEY}")
                .build()
        )
    }
}