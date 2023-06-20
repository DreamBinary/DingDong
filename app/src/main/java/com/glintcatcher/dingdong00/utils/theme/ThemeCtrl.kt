package com.glintcatcher.dingdong00.utils.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.glintcatcher.dingdong00.ui.theme.*
import com.glintcatcher.dingdong00.utils.MMKVUtil


val themeList = listOf(
    Theme.Theme0,
    Theme.Theme1,
    Theme.Theme2,
    Theme.Theme3,
    Theme.Theme4,
    Theme.Theme5,
)

val primaryColorList = listOf(primary0, primary1, primary2, primary3, primary4, primary5)


val themeState: MutableState<Theme> by lazy {
    mutableStateOf(themeList[MMKVUtil.getInt("theme")])
}
