package com.glintcatcher.dingdong00.ui.page


import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString

@Composable
fun AboutMe() {
    Text(
        text = MyString.aboutMeContent,
        modifier = Modifier.padding(top = 20.dp),
        style = MyTypography.bodyMedium
    )
}

