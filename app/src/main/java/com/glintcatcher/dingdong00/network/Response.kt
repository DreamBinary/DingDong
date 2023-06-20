package com.glintcatcher.dingdong00.network

import com.glintcatcher.dingdong00.local.RemindEntity

class RemindResponse(
    val code: Int,
    val msg: String,
    val data: List<RemindEntity>
)

class UserResponse(
    val code: Int,
    val msg: String,
    val data: Map<String, String>
)

class IdResponse(
    val code: Int,
    val msg: String,
    val data: String
)

class ImageResponse(
    val code: Int,
    val msg: String,
    val data: RemindEntity
)

class Response(
    val code: Int,
    val msg: String,
    val data: Boolean
)