package com.glintcatcher.dingdong00.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.local.Repository
import com.glintcatcher.dingdong00.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * @author CXQ
 * @date 2022/4/7
 */
@HiltViewModel
class RemindViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val calendar: Calendar by mutableStateOf(Calendar.getInstance(Locale.getDefault()))
    private val _reminds = mutableStateOf(findByTime())
    private val _sortReminds = mutableStateOf(findByTime())
    private val _timeout = mutableStateOf(repository.findBefore())

    val reminds
        get() = _reminds.value
    val sortReminds
        get() = _sortReminds.value
    val timeout
        get() = _timeout.value
    var refreshing by mutableStateOf(false)

    fun refresh() {
        refreshing = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    repository.getRemoteReminds()
                    _reminds.value = _reminds.value
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    delay(1500)
                    refreshing = false
                }
            }
        }
    }

    fun findTimeout() {
        refreshing = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _timeout.value = repository.findBefore()
                delay(1500)
                refreshing = false
            }
        }
    }

    private fun findByTime(numDate: Int = Int.MAX_VALUE, numHour: Int = Int.MAX_VALUE) =
        repository.findNowToAfter(
            TimeUtil.addTime(numDate, numHour)
        ).cachedIn(viewModelScope)

    fun changeTime(numDate: Int, numHour: Int) {
        _reminds.value = findByTime(numDate, numHour)
    }

    fun findChooseTime() {
        _reminds.value = repository.findNowToAfter(TimeUtil.dateFormat(calendar.time))
    }

    fun findByTab(tabName: String? = null) {
        if (tabName == null) {
            _sortReminds.value = findByTime()
        } else {
            _sortReminds.value = repository.findAfterByTabName(tabName, TimeUtil.addTime())
        }
    }

    fun deleteRemind(remind: RemindEntity) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.delete(remind)
            }
        }

    fun deleteList(remindList: List<RemindEntity>) {
        viewModelScope.launch{
            repository.deleteList(remindList)
        }
    }

    fun clearRemind() =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clear()
            }
        }

    fun update(remind: RemindEntity) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.update(remind)
            }
        }
}
