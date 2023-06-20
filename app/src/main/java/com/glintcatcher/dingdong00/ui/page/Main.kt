package com.glintcatcher.dingdong00.ui.page

import NavigationCtrl
import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.glintcatcher.dingdong00.ui.component.Drawer
import com.glintcatcher.dingdong00.ui.component.TopBar
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.viewmodel.InsertViewModel
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun Main(
    remindViewModel: RemindViewModel,
    insertViewModel: InsertViewModel,
    tabViewModel: TabViewModel,
    expand: Boolean,
    scanClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val title = remember {
        mutableStateOf(MyString.home)
    }
    val navCtrl = rememberAnimatedNavController()
    val homePagerState = rememberPagerState(0)
    val remindList =
        if (homePagerState.currentPage == 0)
            remindViewModel.reminds.collectAsLazyPagingItems().itemSnapshotList.items
        else remindViewModel.sortReminds.collectAsLazyPagingItems().itemSnapshotList.items
    SetSystemColor()
    BottomSheetScaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        backgroundColor = MyTheme.colors.background,
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(scope, scaffoldState, navCtrl)
        },
        drawerBackgroundColor = MyTheme.colors.background,
        topBar = {
            TopBar(
                title = title.value,
                navCtrl = navCtrl,
                { remindViewModel.clearRemind() },
                { remindViewModel.deleteList(remindList) }
            ) { scope.launch { scaffoldState.drawerState.open() } }
        },
        sheetContent = {
            Sheet(
                insertViewModel,
                tabViewModel,
                scope,
                scaffoldState,
                scanClick,
            )
            if (expand) {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = MyTheme.colors.sheetBackground,
    ) {
        NavigationCtrl(
            navCtrl,
            title,
            homePagerState,
            remindViewModel,
            tabViewModel,
            scope,
            scaffoldState
        ) {
            insertViewModel.update(it)
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        }
    }
}


@Composable
private fun SetSystemColor() {
    rememberSystemUiController().run {
        setStatusBarColor(MyTheme.colors.topBar, false)
        setSystemBarsColor(MyTheme.colors.topBar, false)
        setNavigationBarColor(MyTheme.colors.bottomBar, true)
    }
}