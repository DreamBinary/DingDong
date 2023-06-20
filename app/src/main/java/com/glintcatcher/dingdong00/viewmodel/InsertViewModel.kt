package com.glintcatcher.dingdong00.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.local.Repository
import com.glintcatcher.dingdong00.utils.TimeUtil
import com.glintcatcher.dingdong00.utils.calendar.CalendarUtil
import com.glintcatcher.dingdong00.utils.scan.Scan
import com.glintcatcher.dingdong00.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

@HiltViewModel
class InsertViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val calendar: Calendar by mutableStateOf(Calendar.getInstance(Locale.getDefault()))
    val date = mutableStateOf(TimeUtil.dateFormat(calendar.time))
    val time = mutableStateOf(TimeUtil.timeFormat(calendar.time))

    private val newRemind = mutableStateOf(
        RemindEntity(
            "",
            "",
            "",
            "其他"
        )
    )

    val name get() = newRemind.value.name
    val tab get() = newRemind.value.tab

    // remindWithId
    fun changeName(newName: String) {
        newRemind.value = newRemind.value.copy(name = newName)
    }

    fun changeTab(newTab: String) {
        newTab.toast()
        newRemind.value = newRemind.value.copy(tab = newTab)
    }

    private fun changeTime() {
        newRemind.value = newRemind.value.copy(
            expireTime = "${date.value} ${time.value}",
            createTime = TimeUtil.nowDate
        )
    }

    fun getScanInfo(code: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val json = Scan.getInfo(code).getJSONObject("showapi_res_body")
                withContext(Dispatchers.Main) {
                    if (json["flag"] == true) {
                        if (json["goodsName"] != null
                            && json["goodsName"].toString().isNotBlank()
                        ) {
                            changeName(json["goodsName"].toString())
                        } else {
                            "搜索物品失败 请自行输入".toast()
                        }
                        if (json["goodsType"] != null
                            && json["goodsType"].toString().isNotBlank()
                        ) {
                            changeTab(json["goodsType"].toString())
                        } else {
                            "搜索类型失败 请自行输入".toast()
                        }
                    } else {
                        json["remark"].toString().toast()
                    }
                }
            }
        }
    }

    fun insert(context: Context, showing: Int, image: Bitmap? = null, uri: Uri? = null) {
        changeTime()
        viewModelScope.launch {
            // 日历
            CalendarUtil.insert(name, name, calendar.timeInMillis, calendar.timeInMillis + 30, 30)

            try {
                val id = repository.insert(newRemind.value)
                newRemind.value.id = abs(Random.nextLong())
                val json = JSON.toJSONString(newRemind.value)
                println(json)
                val body = json.toRequestBody("application/json".toMediaTypeOrNull())
                val response = repository.insertRemoteReminds(body)
                response.msg.toast()
                if (response.code == 200) {
                    if (showing == 1 && image != null) {
                        upTake(context, image)
                    } else if (showing == 2 && uri != null) {
                        upPick(uri)
                    }
                }
            } catch (e: Exception) {
                "插入服务端失败".toast()
                e.printStackTrace()
            } finally {
                changeName("")
            }

        }
    }

    fun update(remindEntity: RemindEntity) {
        newRemind.value = remindEntity
        val dateAndTime = remindEntity.expireTime.split(" ")
        date.value = dateAndTime[0]
        time.value = dateAndTime[1]
    }

    private fun upTake(context: Context, image: Bitmap) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val file = File(context.externalCacheDir, "${newRemind.value.id}.jpg")
                    val bos = BufferedOutputStream(FileOutputStream(file))
                    image.compress(Bitmap.CompressFormat.JPEG, 80, bos)
                    bos.flush()
                    bos.close()
                    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val body =
                        MultipartBody.Part.createFormData("image", file.name, requestFile)
                    val resp = repository.upImage(newRemind.value.id, body)
                    println(resp.code)
                    if (resp.code == 200) {
                        repository.insert(resp.data)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun upPick(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val file = uri.toFile()
                    val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val body =
                        MultipartBody.Part.createFormData("image", file.name, requestFile)
                    val resp = repository.upImage(newRemind.value.id, body)
                    if (resp.code == 200) {
                        repository.insert(resp.data)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}