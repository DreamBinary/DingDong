package com.glintcatcher.dingdong00.utils.scan

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Scan {
    private const val BaseUrl =
        "https://route.showapi.com/"
    private val params = mapOf(
        "showapi_appid" to "993764",
        "showapi_sign" to "6ae58b932c824f2aae8b3663f21b47a9"
    )
    private val scanService = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ScanService::class.java)

    suspend fun getInfo(code: String) =
        scanService.callInfo(params, code)
}