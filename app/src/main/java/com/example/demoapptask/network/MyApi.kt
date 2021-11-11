package com.example.demoapptask.network

import com.example.demoapptask.response.MyResponse
import com.example.demoapptask.UserClass
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.POST


interface MyApi {


    @POST("getfeatured")
    fun getList(
        @Header("Authorization") authorization: String,
        @Body userClass: UserClass
    ): Call<MyResponse>

}