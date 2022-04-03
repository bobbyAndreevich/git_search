package com.example.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitService {

    @GET("search/users")
    suspend fun getUsersList(@Header("accept") accept: String,
                      @Query("q") searchQuery: String,
                      @Query("per_page") results: Int,
                      @Query("page") pageNumber: Int,
                      ): Response<UserListResponseMap>
}