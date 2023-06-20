package com.glintcatcher.dingdong00.ui.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.route.Route
import com.glintcatcher.dingdong00.utils.route.RouteName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class DrawerItem(val iconId: Int, val itemName: String)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    navCtrl: NavHostController
) {
    val items = listOf(
        RouteName.LOGIN to DrawerItem(R.drawable.ic_login, MyString.login),
        RouteName.TIMEOUT to DrawerItem(R.drawable.ic_timeout, MyString.timeout),
        RouteName.SETTING to DrawerItem(R.drawable.ic_setting, MyString.setting)
    )
    Column {
        DrawerHead()
        DrawerBody(items = items, navCtrl = navCtrl) {
            scope.launch { scaffoldState.drawerState.close() }
        }
    }
    BackHandler(enabled = scaffoldState.drawerState.isOpen) {
        scope.launch {
            scaffoldState.drawerState.close()
        }
    }
}

@Composable
private fun DrawerHead() {
    //            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(R.drawable.ic_drink)
//
//                    .crossfade(true)
//                    .placeholder(R.drawable.ic_launch)
//                    .error(R.drawable.ic_icon)
//                    .transformations(CircleCropTransformation())
//                    .build(), contentDescription = null,
//                modifier = Modifier
//                    .height(100.dp)
//                    .width(100.dp)
//            )
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MyTheme.colors.drawerHeader)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launch),
                contentDescription = null,
                Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .border(2.dp, MyTheme.colors.stroke, CircleShape),
            )
        }
    }
}

@Composable
private fun DrawerBody(
    items: List<Pair<String, DrawerItem>>,
    navCtrl: NavHostController,
    onClick: () -> Unit
) {
    val backStackEntry by navCtrl.currentBackStackEntryAsState()
    val current = backStackEntry?.destination
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(items) {
            ItemRow(drawerItem = it.second) {
                Route.navigate(navCtrl, current, it.first)
                onClick()
            }
        }
    }
}

@Composable
private fun ItemRow(drawerItem: DrawerItem, onClick: () -> Unit) {
    DropdownMenuItem(
        onClick = onClick, Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(5.dp)
    ) {
        Icon(
            painter = painterResource(id = drawerItem.iconId),
            contentDescription = drawerItem.itemName,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically),
            tint = MyTheme.colors.primary
        )
        Text(
            text = drawerItem.itemName,
            style = MyTypography.drawer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
        )
    }
}