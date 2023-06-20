package com.glintcatcher.dingdong00.network

import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author CXQ
 * @date 2022/5/25
 */
class UserRepository @Inject constructor() {
    @Inject
    @BindUser
    lateinit var service: UserService

    suspend fun getId() = service.callId()

    suspend fun signUp(body: RequestBody) = service.callSignUp(body)

    suspend fun signIn(body: RequestBody) = service.callSignIn(body)

}