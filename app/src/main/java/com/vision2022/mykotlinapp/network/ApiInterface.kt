package com.vision2022.mykotlinapp.network

import com.vision2022.mykotlinapp.model.DataModel
import com.vision2022.mykotlinapp.model.LoginResponse
import com.vision2022.mykotlinapp.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {

    @GET("users/get/photo")
    fun getPhotos(): Call<List<DataModel>>

    @POST("authenticate")
    fun login(@Body user: User): Call<LoginResponse>

}
