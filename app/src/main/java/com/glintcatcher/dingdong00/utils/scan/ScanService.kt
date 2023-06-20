package com.glintcatcher.dingdong00.utils.scan

import com.alibaba.fastjson.JSONObject
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ScanService {
    @Headers("Content-Type: application/json")
    @GET("66-22")
    suspend fun callInfo(
        @QueryMap params: Map<String, String>,
        @Query("code") code: String
    ): JSONObject
}