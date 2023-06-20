package com.glintcatcher.dingdong00.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author CXQ
 * @date 2022/5/19
 */
@Entity(tableName = "tab")
data class TabEntity(
    var name: String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)