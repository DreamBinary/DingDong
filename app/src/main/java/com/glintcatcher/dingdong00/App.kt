package com.glintcatcher.dingdong00

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

/**
 * @author CXQ
 * @date 2022/4/9
 */

@HiltAndroidApp
class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        val dir = filesDir.absolutePath + "/mmkv"
        MMKV.initialize(this, dir)
        Log.d("TAG", "onCreate: $dir")
        CONTEXT = this
    }
}
