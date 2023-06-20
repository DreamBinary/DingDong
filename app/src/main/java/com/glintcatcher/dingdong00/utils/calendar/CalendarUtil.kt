package com.glintcatcher.dingdong00.utils.calendar

import com.glintcatcher.dingdong00.App
import com.glintcatcher.dingdong00.utils.toast
import com.kyle.calendarprovider.calendar.CalendarEvent
import com.kyle.calendarprovider.calendar.CalendarProviderManager

object CalendarUtil {

    fun insert(
        title: String,
        description: String,
        start: Long,
        end: Long,
        advanceTime: Int,
        eventLocation: String? = null,
        rRule: String? = null
    ) {
        val event = CalendarEvent(title, description, eventLocation, start, end, advanceTime, rRule)
        when (CalendarProviderManager.addCalendarEvent(App.CONTEXT, event)) {
            0 -> {
                "插入日历成功".toast()
            }
            -1 -> {
                "插入日历失败".toast()
            }
            -2 -> {
                "插入日历失败 没有权限".toast()
            }
        }
    }
}