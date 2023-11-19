package com.waffle.core.api

import com.waffle.core.data.response.PopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularList() : Response<PopularResponse>
}