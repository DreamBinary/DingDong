package com.glintcatcher.dingdong00.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.ui.page.SettingItem
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.theme.primaryColorList
import com.glintcatcher.dingdong00.utils.theme.themeState

@Composable
fun SettingTheme() {
    var showDialogState by remember {
        mutableStateOf(false)
    }
    if (showDialogState) {
        ThemePicker(primaryColorList, themeState) { showDialogState = false }
    }
    SettingItem(
        text = MyString.settingTheme,
        end = {
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
                    .padding(5.dp),
                shape = CircleShape,
                color = MyTheme.colors.primary,
            ) {}
        },
        onclick = {
            showDialogState = true
        }
    )
}