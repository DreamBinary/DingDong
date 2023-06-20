package com.glintcatcher.dingdong00.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.glintcatcher.dingdong00.network.BindRemind
import com.glintcatcher.dingdong00.network.RemindService
import com.glintcatcher.dingdong00.utils.TimeUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @author CXQ
 * @date 2022/4/9
 */
class Repository @Inject constructor(
    private val remindDao: RemindDao,
    private val tabDao: TabDao
) {

    @BindRemind
    @Inject
    lateinit var service: RemindService

    private fun pageData(pagingSourceFactory: () -> PagingSource<Int, RemindEntity>) = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = pagingSourceFactory
    ).flow

    suspend fun insert(remindEntity: RemindEntity) =
        remindDao.insert(remindEntity)

    suspend fun insertList(remindList: List<RemindEntity>) =
        remindDao.insertList(remindList)

    suspend fun delete(remindEntity: RemindEntity) =
        remindDao.delete(remindEntity)

    suspend fun deleteList(remindList: List<RemindEntity>) =
        remindDao.deleteList(remindList)

    suspend fun clear() =
        remindDao.clear()

    suspend fun update(remindEntity: RemindEntity) =
        remindDao.update(remindEntity)

    fun findBefore() =
        pageData { remindDao.findAllBefore(TimeUtil.addTime()) }

    fun findNowToAfter(afterTime: String) =
        pageData { remindDao.findAllBetween(TimeUtil.addTime(), afterTime) }

    fun findAfterByTabName(tabName: String, currentTime: String) =
        pageData { remindDao.findAfterByTabName(tabName, currentTime) }

    suspend fun getRemoteReminds() {
        val response = service.callFindAllRemind()
        if (response.code == 200) {
            val remindList = response.data
            remindDao.insertList(remindList)
        }
    }

    suspend fun insertRemoteReminds(body: RequestBody) =
        service.callRemindInsert(body)

    suspend fun clearRemoteTimeOut(username: Long, nowTime: String) =
        service.callRemindTimeOutClear(username, nowTime)

    // tab
    suspend fun insertTab(tab: TabEntity) =
        tabDao.insert(tab)

    suspend fun insertAllTab(tabs: List<TabEntity>) =
        tabDao.insertAll(tabs)

    suspend fun deleteTab(tab: TabEntity) =
        tabDao.delete(tab)

    fun findTab() = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { tabDao.find() }
    ).flow

    suspend fun upImage(remindId: Long, image: MultipartBody.Part) =
        service.callInsertImage(remindId, image)


}
