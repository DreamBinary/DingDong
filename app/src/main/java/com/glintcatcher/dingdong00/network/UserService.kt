package com.glintcatcher.dingdong00.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserService {

    @Headers("Content-Type: application/json")
    @GET("getUUID")
    suspend fun callId(): IdResponse

    @Headers("Content-Type: application/json")
    @POST("signUp")
    suspend fun callSignUp(@Body body: RequestBody): UserResponse

    @Headers("Content-Type: application/json")
    @POST("signIn")
    suspend fun callSignIn(@Body body: RequestBody): UserResponse

    @Headers("Content-Type: application/json")
    @DELETE("delete")
    suspend fun callLogout(): Response

    @Multipart
    @POST("upload")
    suspend fun callUpImage(@Part image: MultipartBody.Part): Response

    @GET("download?")
    suspend fun callGetImage(@Query("username") username: String): ResponseBody


}