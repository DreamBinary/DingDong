package com.glintcatcher.dingdong00.local

import androidx.paging.PagingSource
import androidx.room.*

/**
 * @author CXQ
 * @date 2022/5/26
 */
@Dao
interface TabDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg tabEntity: TabEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tabs: List<TabEntity>)

    @Delete
    suspend fun delete(vararg tabEntity: TabEntity)

    @Query("select * from tab order by id")
    fun find(): PagingSource<Int, TabEntity>

    @Query("delete from tab")
    suspend fun deleteAll()

}