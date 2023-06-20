package com.glintcatcher.dingdong00.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface RemindDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remindEntity: RemindEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(remindList: List<RemindEntity>)

    @Delete
    suspend fun delete(vararg remindEntity: RemindEntity)

    @Delete
    suspend fun deleteList(remindList: List<RemindEntity>)

    @Query("delete from remindWithId")
    suspend fun clear()

    @Query("delete from remindWithId where tab = :tab")
    suspend fun deleteByTabName(tab: String)

    @Update
    suspend fun update(vararg remindEntity: RemindEntity)

    @Query("select * from remindWithId where expire_time >= :nowTime order by expire_time")
    fun findAllAfter(nowTime: String): PagingSource<Int, RemindEntity>

    @Query("select * from remindWithId where expire_time < :nowTime order by expire_time desc")
    fun findAllBefore(nowTime: String): PagingSource<Int, RemindEntity>

    @Query("select * from remindWithId where expire_time < :time2 and expire_time > :time1 order by expire_time")
    fun findAllBetween(time1: String, time2: String): PagingSource<Int, RemindEntity>

    @Query("select * from remindWithId where tab = :tab and expire_time >= :nowTime order by expire_time")
    fun findAfterByTabName(
        tab: String,
        nowTime: String
    ): PagingSource<Int, RemindEntity>

    @Query("select * from remindWithId where tab = :tab and expire_time < :nowTime order by expire_time desc")
    fun findByTabNameBefore(
        tab: String,
        nowTime: String
    ): PagingSource<Int, RemindEntity>

}
