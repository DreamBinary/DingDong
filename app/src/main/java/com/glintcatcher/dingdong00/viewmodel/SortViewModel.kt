//package com.glintcatcher.dingdong00.viewmodel
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.glintcatcher.dingdong00.local.RemindEntity
//import com.glintcatcher.dingdong00.local.Repository
//import com.glintcatcher.dingdong00.utils.TimeUtil
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
///**
// * @author CXQ
// * @date 2022/5/19
// */
//@HiltViewModel
//class SortViewModel @Inject constructor(
//    private val repository: Repository
//) : ViewModel() {
//
//    private val _reminds = mutableStateOf(repository.findAllAfter(TimeUtil.addTime()))
//
//    val remindList
//        get() = _reminds.value
//
//    // remindWithId
//    fun findAllAfter() {
//        _reminds.value = repository.findAllAfter(TimeUtil.addTime())
//    }
//
//    fun findByTab(tabName: String) {
//        _reminds.value = repository.findAfterByTabName(tabName, TimeUtil.addTime())
//    }
//
//    fun deleteRemind(remindWithId: RemindEntity) =
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                repository.delete(remindWithId)
//            }
//        }
//
//    fun deleteByTabName(tabName: String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                repository.deleteByTabName(tabName)
//            }
//        }
//    }
//}