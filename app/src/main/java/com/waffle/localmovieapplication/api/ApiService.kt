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

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ) : Response<DiscoverDetailResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReview(
        @Path("id") id: Int,
        @Query("page") page: Int
    ) : Response<DiscoverReviewResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailer(
        @Path("id") id: Int
    ) : Response<DiscoverThrillerResponse>
}