package com.glintcatcher.dingdong00.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RemindEntity::class, TabEntity::class], version = 1, exportSchema = false)
abstract class RemindDb : RoomDatabase() {

    abstract fun remindDao(): RemindDao
    abstract fun tabDao(): TabDao

    companion object {
        private const val DB_NAME = "remind_db"

        @Volatile
        private var INSTANCE: RemindDb? = null

        @Synchronized
        fun getDatabase(context: Context): RemindDb =
            synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, RemindDb::class.java, DB_NAME)
                .build()
    }

}
