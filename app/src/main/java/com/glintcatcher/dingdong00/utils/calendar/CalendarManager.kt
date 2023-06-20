//package com.glintcatcher.dingdong00.utils.calendar
//
//import android.annotation.SuppressLint
//import android.content.ContentUris
//import android.content.ContentValues
//import android.content.Context
//import android.content.pm.PackageManager
//import android.icu.util.TimeZone
//import android.net.Uri
//import android.os.Build
//import android.provider.CalendarContract
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.core.content.contentValuesOf
//import com.kyle.calendarprovider.calendar.CalendarEvent
//
//class CalendarManager {
//
//    private val CALENDAR_NAME = "DDD"
//    private val ACCOUNT_NAME = "DD"
//    private val CALENDAR_DISPLAY_NAME = "DD账户"
//
//    //    // 获取账户
//    @RequiresApi(Build.VERSION_CODES.N)
//    @SuppressLint("Recycle", "Range")
//    fun getAccountId(context: Context): Long {
//        val cursor = context.contentResolver.query(
//            CalendarContract.Calendars.CONTENT_URI,
//            null, null, null, null
//        )
//        return if (cursor != null && cursor.columnCount > 0) {
//            cursor.moveToFirst()
//            cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID))
//        } else {
//            createAccount(context)
//        }
//    }
//
//    // 创建账户
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun createAccount(context: Context): Long {
//        var uri = CalendarContract.Calendars.CONTENT_URI
//        val accountUri: Uri?
////        val account = ContentValues().apply {
////            put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
////            put(CalendarContract.Calendars.NAME, CALENDAR_NAME)
////            put(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
////            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_DISPLAY_NAME)
////            put(
////                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
////                CalendarContract.Calendars.CAL_ACCESS_OWNER
////            )
////            put(CalendarContract.Calendars.VISIBLE, true)
////            put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, TimeZone.getDefault().id)
////            // 可修改时区
////            put(CalendarContract.Calendars.CAN_MODIFY_TIME_ZONE, true)
////            put(CalendarContract.Calendars.SYNC_EVENTS, true)
////            put(CalendarContract.Calendars.OWNER_ACCOUNT, ACCOUNT_NAME)
////            put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, true)
////            put(CalendarContract.Calendars.MAX_REMINDERS, 10)
////            //
////            put(CalendarContract.Calendars.ALLOWED_REMINDERS, "0, 1, 2, 3, 4")
////            put(CalendarContract.Calendars.ALLOWED_AVAILABILITY, "0, 1, 2")
////            put(CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES, "0, 1, 2")
////        }
//
//        val account = contentValuesOf(
//            CalendarContract.Calendars.ACCOUNT_TYPE to CalendarContract.ACCOUNT_TYPE_LOCAL,
//            CalendarContract.Calendars.NAME to CALENDAR_NAME,
//            CalendarContract.Calendars.ACCOUNT_NAME to ACCOUNT_NAME,
//            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME to CALENDAR_DISPLAY_NAME,
//            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL to CalendarContract.Calendars.CAL_ACCESS_OWNER,
//            CalendarContract.Calendars.VISIBLE to true,
//            CalendarContract.Calendars.CALENDAR_TIME_ZONE to TimeZone.getDefault().id,
//            // 可修改时区
//            CalendarContract.Calendars.CAN_MODIFY_TIME_ZONE to true,
//            CalendarContract.Calendars.SYNC_EVENTS to true,
//            CalendarContract.Calendars.OWNER_ACCOUNT to ACCOUNT_NAME,
//            CalendarContract.Calendars.CAN_ORGANIZER_RESPOND to true,
//            CalendarContract.Calendars.MAX_REMINDERS to 10,
//            //
//            CalendarContract.Calendars.ALLOWED_REMINDERS to "0, 1, 2, 3, 4",
//            CalendarContract.Calendars.ALLOWED_AVAILABILITY to "0, 1, 2, 3, 4",
//            CalendarContract.Calendars.ALLOWED_ATTENDEE_TYPES to "0, 1, 2, 3, 4"
//        )
//        uri = uri.buildUpon()
//            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
//            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
//            .appendQueryParameter(
//                CalendarContract.Calendars.ACCOUNT_TYPE,
//                CalendarContract.Calendars.CALENDAR_LOCATION
//            )
//            .build()
//
//        accountUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (PackageManager.PERMISSION_GRANTED == context.checkSelfPermission(
//                    "android.permission.WRITE_CALENDAR"
//                )
//            ) {
//                context.contentResolver.insert(uri, account)
//            } else {
//                return -2
//            }
//        } else {
//            context.contentResolver.insert(uri, account)
//        }
//        return if (accountUri == null) {
//            -1
//        } else {
//            ContentUris.parseId(accountUri)
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun insertEvent(context: Context, event: CalendarEvent) {
//        val calendarId = getAccountId(context)
//        Log.d("TAG", calendarId.toString())
//        val values = ContentValues().apply {
//            put(CalendarContract.Events.CALENDAR_ID, calendarId)
//            put(CalendarContract.Events.DTSTART, event.start)
//            put(CalendarContract.Events.DTEND, event.end)
//            put(CalendarContract.Events.TITLE, event.title)
//            put(CalendarContract.Events.DESCRIPTION, event.description)
//            put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
//            put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_DEFAULT)
//            put(CalendarContract.Events.STATUS, 0)
//            put(CalendarContract.Events.HAS_ALARM, 1)
//            put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
//
//        }
//        val uri = context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
//        uri?.let {
//            val eventId = uri.lastPathSegment?.toLong()
//            val reminder = ContentValues().apply {
//                put(CalendarContract.Reminders.EVENT_ID, eventId)
//                put(CalendarContract.Reminders.MINUTES, event.advanceTime)
//                put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM)
//            }
//            context.contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminder)
//        }
//    }
//}