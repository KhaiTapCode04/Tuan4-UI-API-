package com.example.th2_danghuynhkhai

import retrofit2.Call
import retrofit2.http.GET

interface TaskApi {
    @GET("https://amock.io/api/researchUTH/tasks")
    fun getTasks(): Call<ApiResponse>
}