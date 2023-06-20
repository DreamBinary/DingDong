package com.glintcatcher.dingdong00.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.local.Repository
import com.glintcatcher.dingdong00.local.TabEntity
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.MMKVUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _defaultTabs = listOf(
        TabEntity(MyString.food, id = 0),
        TabEntity(MyString.drink, id = 1),
        TabEntity(MyString.drug, id = 2),
        TabEntity(MyString.ticket, id = 3),
        TabEntity(MyString.makeup, id = 4),
    )

    private var _tabs = mutableStateOf(repository.findTab())
    val tabs
        get() = _tabs.value

    // pop
    val add = mutableStateOf(false)
    val newTab = mutableStateOf(TabEntity(""))

    init {
        if (!MMKVUtil.getBoolean("first")) {
            viewModelScope.launch {
                repository.insertAllTab(_defaultTabs)
            }
            MMKVUtil.put("first", true)
        }
    }

    // tab
    fun insertTab() {
        viewModelScope.launch {
            repository.insertTab(newTab.value)
        }
    }

    fun changeNewTabName(newTabName: String) {
        newTab.value = newTab.value.copy(name = newTabName)
    }

    fun deleteTab(tab: TabEntity) {
        viewModelScope.launch {
            repository.deleteTab(tab)
        }
    }

    fun getIcon(name: String): Int =
        when (name) {
            MyString.all -> R.drawable.ic_all
            MyString.food -> R.drawable.ic_food
            MyString.drink -> R.drawable.ic_drink
            MyString.drug -> R.drawable.ic_drug
            MyString.ticket -> R.drawable.ic_ticket
            MyString.makeup -> R.drawable.ic_makeup
            else -> R.drawable.ic_other
        }
}