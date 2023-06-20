package com.glintcatcher.dingdong00.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author CXQ
 * @date 2022/4/9
 */
@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideRemindDb(@ApplicationContext context: Context): RemindDb = Room.databaseBuilder(
        context,
        RemindDb::class.java,
        "remind_db"
    ).build()

    @Provides
    @Singleton
    fun provideRemindDao(remindDb: RemindDb): RemindDao = remindDb.remindDao()

    @Provides
    @Singleton
    fun provideTabDao(remindDb: RemindDb): TabDao = remindDb.tabDao()
}
