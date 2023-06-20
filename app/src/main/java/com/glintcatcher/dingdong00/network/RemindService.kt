package com.glintcatcher.dingdong00.network

import com.glintcatcher.dingdong00.utils.MMKVUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RemindService {
    @Headers("Content-Type: application/json")
    @GET("findAll")
    suspend fun callFindAllRemind(
        @Header("token") token: String = MMKVUtil.getString("token"),
    ): RemindResponse

    @Headers("Content-Type: application/json")
    @POST("insert")
    suspend fun callRemindInsert(
        @Body body: RequestBody,
        @Header("token") token: String = MMKVUtil.getString("token"),
    ): Response

    @Headers("Content-Type: application/json")
    @DELETE("clearTimeOut")
    suspend fun callRemindTimeOutClear(
        @Query("userId") userId: Long,
        @Query("nowTime") nowTime: String
    ): Response

    @Multipart
    @POST("insertImage")
    suspend fun callInsertImage(
        @Query("id") remindId: Long,
        @Part image: MultipartBody.Part,
        @Header("token") token: String = MMKVUtil.getString("token"),
    ): ImageResponse


}