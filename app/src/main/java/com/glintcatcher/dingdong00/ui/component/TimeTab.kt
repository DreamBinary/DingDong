package com.glintcatcher.dingdong00.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.TimeSelect.selfSelect
import com.glintcatcher.dingdong00.utils.toast
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel

/**
 * @author CXQ
 * @date 2022/4/8
 */
@Composable
fun TabTimeRow(viewModel: RemindViewModel) {
    val context = LocalContext.current
    val tabs = listOf(
        MyString.all to (Int.MAX_VALUE to Int.MAX_VALUE),
        MyString.oneHour to (0 to 1),
        MyString.oneDay to (1 to 0),
        MyString.sevenDays to (7 to 0),
        MyString.selectSelf to (0 to 0)
    )
    var selected by remember {
        mutableStateOf(0)
    }

    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) Color(0xff636366) else Color(0xffe7e8e3))
    ) {
        for (i in tabs.indices) {
            TabTime(
                Modifier
                    .weight(1f)
                    .padding(5.dp),
                selected = selected == i,
                text = tabs[i].first
            ) {
                if (i == 4) {
                    selfSelect(context, viewModel) { selected = i }
                } else {
                    selected = i
                    tabs[i].first.toast()
                    viewModel.changeTime(tabs[i].second.first, tabs[i].second.second)
                }
            }
        }
    }
}

@Composable
private fun TabTime(modifier: Modifier, selected: Boolean, text: String, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (selected) MyTheme.colors.tab else Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.elevation()
    ) {
        Text(text, style = MyTypography.bodySmall, color = Color.Black)
    }
}
