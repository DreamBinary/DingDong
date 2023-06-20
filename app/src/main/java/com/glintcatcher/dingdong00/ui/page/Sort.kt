package com.glintcatcher.dingdong00.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.local.TabEntity
import com.glintcatcher.dingdong00.ui.component.AddTabDialog
import com.glintcatcher.dingdong00.ui.component.RemindLazyColumn
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.toast
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel

@Composable
fun Sort(
    remindViewModel: RemindViewModel,
    tabViewModel: TabViewModel,
    onUpdate: (RemindEntity) -> Unit
) {
    val reminds = remindViewModel.sortReminds.collectAsLazyPagingItems()
    Row {
        SortList(remindViewModel, tabViewModel)
        RemindLazyColumn(
            reminds,
            remindViewModel.refreshing,
            onUpdate = onUpdate,
            onDelete = { remind ->
                remindViewModel.deleteRemind(remind)
            }
        ) { remindViewModel.refresh() }
    }
}

@Composable
fun SortList(remindViewModel: RemindViewModel, tabViewModel: TabViewModel) {
    val selected = remember {
        mutableStateOf(-1)
    }
    val tabList =
        tabViewModel.tabs.collectAsLazyPagingItems().itemSnapshotList.items.plus(TabEntity(MyString.other))

    Column(
        modifier = Modifier
            .width(150.dp)
            .fillMaxHeight()
            .background(MyTheme.colors.sortBackground),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddTabDialog(tabViewModel)
        AddButton {
            tabViewModel.add.value = true
        }
        val all = TabEntity(MyString.all)
        SortItem(all, selected.value == -1, {
            tabViewModel.getIcon(it)
        }, {
            selected.value = -1
            all.name.toast()
            remindViewModel.findByTab()
        })

        LazyColumn(
            contentPadding = PaddingValues(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 60.dp),
        ) {
            itemsIndexed(tabList) { index, item ->
                SortItem(item, selected.value == index, {
                    tabViewModel.getIcon(it)
                }, {
                    selected.value = index
                    item.name.toast()
                    remindViewModel.findByTab(item.name)
                }) {
                    tabViewModel.deleteTab(item)
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SortItem(
    tab: TabEntity,
    selected: Boolean,
    getIcon: (String) -> Int,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
) {
    val showDelete = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(130.dp)
            .height(45.dp)
            .combinedClickable(
                onClick = {
                    onClick()
                    showDelete.value = false
                }, onLongClick = { showDelete.value = !showDelete.value }
            ),
        shape = RoundedCornerShape(13.dp),
        backgroundColor = MyTheme.colors.sortBackground,
        border = BorderStroke(if (selected) 4.dp else 2.dp, MyTheme.colors.stroke),
        elevation = 2.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(!showDelete.value) {
                Icon(
                    painter = painterResource(getIcon(tab.name)),
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                        .width(50.dp),
                    tint = MyTheme.colors.primary
                )
            }
            AnimatedVisibility(showDelete.value) {
                Spacer(modifier = Modifier.padding(start = 10.dp))
            }
            Text(
                text = tab.name,
                style = MyTypography.sortTab,
                modifier = Modifier.weight(1.0f)
            )
            AnimatedVisibility(onLongClick != null && showDelete.value) {
                IconButton(onClick = onLongClick!!) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable(onClick = onClick)
            .width(130.dp)
            .height(45.dp),
        shape = RoundedCornerShape(13.dp),
        backgroundColor = MyTheme.colors.sortBackground,
        border = BorderStroke(2.dp, MyTheme.colors.stroke),
        elevation = 2.dp
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            tint = MyTheme.colors.primary
        )
    }
}

