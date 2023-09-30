package com.waffle.localmovieapplication.api

import com.waffle.localmovieapplication.data.response.DiscoverDetailResponse
import com.waffle.localmovieapplication.data.response.DiscoverReviewResponse
import com.waffle.localmovieapplication.data.response.DiscoverThrillerResponse
import com.waffle.localmovieapplication.data.response.PopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularList(
        @Query("page") page: Int
    ) : Response<PopularResponse>
}