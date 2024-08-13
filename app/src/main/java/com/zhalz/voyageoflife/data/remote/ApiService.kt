package com.zhalz.voyageoflife.data.remote

import com.zhalz.voyageoflife.data.remote.response.LoginResponse
import com.zhalz.voyageoflife.data.remote.response.RegisterResponse
import com.zhalz.voyageoflife.data.remote.response.StoriesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUNFZmJvLWl0MVlqcndkNF8iLCJpYXQiOjE3MjM1NDkxMTl9.LYAQ_ExB1Elqk-lQlLRIwpuMedonHdafvIpMPxvwkFs"
    ) : StoriesResponse

}