package com.glintcatcher.dingdong00.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remindWithId")
data class RemindEntity(
    @ColumnInfo(name = "expire_time")
    val expireTime: String,
    @ColumnInfo(name = "create_time")
    val createTime: String,
    val name: String,
    val tab: String,
    val image: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
