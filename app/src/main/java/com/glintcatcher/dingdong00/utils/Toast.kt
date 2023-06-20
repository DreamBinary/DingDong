package com.glintcatcher.dingdong00.utils

import android.widget.Toast
import com.glintcatcher.dingdong00.App

fun String.toast() =
    Toast.makeText(App.CONTEXT, this, Toast.LENGTH_SHORT).show()