package com.glintcatcher.dingdong00.network

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    @BindRemind
    fun provideRemindService(okHttpClient: OkHttpClient): RemindService =
        Retrofit.Builder()
            .baseUrl(REMIND_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(RemindService::class.java)

    @Singleton
    @Provides
    @BindUser
    fun provideUserService(okHttpClient: OkHttpClient): UserService =
        Retrofit.Builder()
            .baseUrl(USER_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(UserService::class.java)

}