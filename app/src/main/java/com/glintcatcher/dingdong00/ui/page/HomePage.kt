package com.glintcatcher.dingdong00.ui.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage(
    pageState: PagerState,
    remindViewModel: RemindViewModel,
    tabViewModel: TabViewModel,
    onRemindUpdate: (RemindEntity) -> Unit,
    onFloatClick: () -> Unit
) {

    val labels =
        listOf(
            MyString.remind to (R.drawable.ic_remind),
            MyString.sort to (R.drawable.ic_sort),
        )
    val scope = rememberCoroutineScope()
    Scaffold(
        backgroundColor = MyTheme.colors.background,
        bottomBar = {
            BottomAppBar(
                backgroundColor = MyTheme.colors.bottomBar,
                cutoutShape = RoundedCornerShape(100),
            ) {
                for (i in labels.indices) {
                    BottomNavigationItem(
                        selected = pageState.currentPage == i,
                        onClick = {
                            scope.launch {
                                pageState.animateScrollToPage(i)
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    id = labels[i].second
                                ),
                                null,
                                Modifier.padding(5.dp),
                                tint = if (pageState.currentPage == i) MyTheme.colors.primary else MyTheme.colors.primary.copy(
                                    alpha = 0.5f
                                )
                            )
                        },
                        label = { Text(labels[i].first) }
                    )
                }
            }
        },
        floatingActionButton = { FloatingBtn(onFloatClick) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
    ) {
        HorizontalPager(count = labels.size, state = pageState, verticalAlignment = Alignment.Top) {
            when (it) {
                0 -> Reminds(remindViewModel, onRemindUpdate)
                1 -> Sort(remindViewModel, tabViewModel, onRemindUpdate)
            }
        }
    }
}

@Composable
private fun FloatingBtn(onFloatClick: () -> Unit) {
    FloatingActionButton(
        backgroundColor = MyTheme.colors.floatingBtn,
        onClick = onFloatClick,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 14.dp,
            hoveredElevation = 10.dp,
            focusedElevation = 10.dp,
        )
    ) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}