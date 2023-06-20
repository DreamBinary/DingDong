package com.glintcatcher.dingdong00.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.MutableState
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import java.util.*

/**
 * @author CXQ
 * @date 2022/5/19
 */
object TimeSelect {
    @SuppressLint("ResourceType")
    fun dateShow(context: Context, calendar: Calendar, date: MutableState<String>? = null) =
        DatePickerDialog(
            context, 3,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date?.value = TimeUtil.dateFormat(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

    fun timeShow(context: Context, calendar: Calendar, time: MutableState<String>? = null) =
        TimePickerDialog(
            context, 3,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                time?.value = TimeUtil.timeFormat(calendar.time)
            }, calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

    fun selfSelect(
        context: Context,
        viewModel: RemindViewModel,
        selected: () -> Unit
    ) {
        var flag = true
        val time = timeShow(
            context,
            viewModel.calendar
        ).apply {
            setOnDismissListener {
                if (flag) {
                    selected()
                    viewModel.findChooseTime()
                }
            }
            setOnCancelListener {
                flag = false
            }
        }
        dateShow(
            context,
            viewModel.calendar
        ).apply {
            setOnDismissListener {
                if (flag) {
                    time.show()
                }
            }
            setOnCancelListener {
                flag = false
            }
        }.show()
    }
}