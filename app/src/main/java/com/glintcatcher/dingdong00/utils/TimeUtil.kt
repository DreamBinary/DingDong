package com.glintcatcher.dingdong00.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import com.glintcatcher.dingdong00.utils.Constans.MyString
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    @SuppressLint("ConstantLocale")
    val nowDate: String =
        SimpleDateFormat(
            MyString.patternDate,
            Locale.getDefault()
        ).format(System.currentTimeMillis())

    @SuppressLint("ConstantLocale")
    val nowTime: String =
        SimpleDateFormat(
            MyString.patternTime,
            Locale.getDefault()
        ).format(System.currentTimeMillis())

    fun dateFormat(date: Date): String = SimpleDateFormat(
        MyString.patternDate,
        Locale.getDefault()
    ).format(date)

    fun timeFormat(date: Date): String =
        SimpleDateFormat(MyString.patternTime, Locale.getDefault()).format(date)

    private fun dateAndTimeFormat(date: Date): String =
        SimpleDateFormat(MyString.patternDateAndTime, Locale.getDefault()).format(date)

    fun addDate(calendar: Calendar, num: Int): String {
        calendar.add(Calendar.DATE, num)
        return dateFormat(calendar.time)
    }

    fun addHour(calendar: Calendar, num: Int): String {
        calendar.add(Calendar.HOUR, num)
        return timeFormat(calendar.time)
    }

    fun addTime(numDate: Int = 0, numHour: Int = 0, numMin: Int = 0): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.DATE, numDate)
        calendar.add(Calendar.HOUR, numHour)
        calendar.add(Calendar.MINUTE, numMin)
        return dateAndTimeFormat(calendar.time)
    }


}