package com.example.mobilliumtask.network

import com.example.mobilliumtask.model.ListData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/v2/discover")
    suspend fun getAllData() : Response<List<ListData>>

}