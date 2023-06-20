package com.glintcatcher.dingdong00.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glintcatcher.dingdong00.ui.component.SettingTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.MMKVUtil
import com.glintcatcher.dingdong00.utils.route.Route
import com.glintcatcher.dingdong00.utils.route.RouteName
import com.glintcatcher.dingdong00.utils.toast

@Composable
fun Setting(navCtrl: NavController, onClear: () -> Unit) {

    val backStackEntry by navCtrl.currentBackStackEntryAsState()
    val current = backStackEntry?.destination

    Column {
        // 登录
        SettingItem(
            text = MyString.login,
            onclick = { Route.navigate(navCtrl, current, RouteName.LOGIN) })

        SettingItem(text = MyString.settingLogout, onclick = {
            onClear()
            MMKVUtil.clearAll()
            Route.navigate(navCtrl, current, RouteName.HOMEPAGE)
            "退出成功".toast()
        })

        SpaceLine()

        SettingTheme()
        SpaceLine()
        SettingItem(
            text = MyString.settingAbout,
            onclick = {
                Route.navigate2(navCtrl, current, RouteName.ABOUT_ME)
            }
        )
    }


}


@Composable
fun SettingItem(
    text: String,
    end: @Composable () -> Unit = {},
    onclick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .clickable(onClick = onclick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = MyTypography.setting1)
        Spacer(modifier = Modifier.weight(1f))
        end()
    }
}


@Composable
fun SpaceLine() {
    Spacer(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .height(1.dp)
    )
}




