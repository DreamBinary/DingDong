package com.glintcatcher.dingdong00.ui.component

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.route.Route
import com.glintcatcher.dingdong00.utils.route.RouteName
import com.glintcatcher.dingdong00.utils.toast


@Composable
fun TopBar(
    title: String,
    navCtrl: NavController,
    menu0: () -> Unit,
    menu1: () -> Unit,
    onClickNav: (() -> Unit)? = null
) {
    val backStackEntry by navCtrl.currentBackStackEntryAsState()
    val current = backStackEntry?.destination
    val menu = listOf(
        "清空数据" to menu0,
        "清空所选" to menu1
    )

    TopAppBar(
        title = { Text(text = title, style = MyTypography.title) },
        navigationIcon = {
            if (onClickNav != null) {
                IconButton(onClick = onClickNav) {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                }
            }
        },
        backgroundColor = MyTheme.colors.topBar,
        actions = {
            HomeButton {
                Route.navigate(navCtrl, current, RouteName.HOMEPAGE)
            }
            Menu(menu)
        }
    )
}

@Composable
fun HomeButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = Icons.Default.Home, contentDescription = null)
    }
}

@Composable
fun Menu(items: List<Pair<String, () -> Unit>>) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
    }

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        items.forEach {
            DropdownMenuItem(onClick = {
                it.second()
                it.first.toast()
                expanded = false
            }) {
                Text(text = it.first)
            }
        }
    }
}