package com.glintcatcher.dingdong00.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.network.IMAGE_URL
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.zj.refreshlayout.SwipeRefreshLayout
import com.zj.refreshlayout.SwipeRefreshStyle

/**
 * @author CXQ
 * @date 2022/4/8
 */

@Composable
fun RemindLazyColumn(
    remindList: LazyPagingItems<RemindEntity>,
    refreshing: Boolean,
    onUpdate: (RemindEntity) -> Unit,
    onDelete: (RemindEntity) -> Unit,
    onRefresh: () -> Unit,
) {

    SwipeRefreshLayout(
        isRefreshing = refreshing,
        onRefresh = { onRefresh() },
        swipeStyle = SwipeRefreshStyle.Translate,
        indicator = {
            LottieHeaderTwo(state = it)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
        ) {
            itemsIndexed(remindList) { index, value ->
                if (value != null) {
                    RemindCard(
                        remind = value,
                        alpha = ((remindList.itemCount - index) * 1.0 / remindList.itemCount).toFloat(),
                        onUpdate = onUpdate,
                        onDelete = onDelete
                    )
                }
            }
        }

        AnimatedVisibility(remindList.itemCount == 0) {
            BoxWithConstraints() {
                if (maxWidth < 300.dp) {
                    Image(
                        painter = painterResource(id = R.drawable.empty),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 35.dp)
                            .fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.empty),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 95.dp)
                            .fillMaxSize()
                    )
                }

            }

        }
    }
}

@Composable
private fun RemindCard(
    remind: RemindEntity,
    alpha: Float,
    onUpdate: (RemindEntity) -> Unit,
    onDelete: (RemindEntity) -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(22.dp),
    ) {
        Surface(
            color = MyTheme.colors.primary.copy(alpha = alpha),
//            shape = RoundedCornerShape(22.dp),
        ) {
            var expanded by remember {
                mutableStateOf(false)
            }
            Column(modifier = Modifier.clickable {
                expanded = !expanded
            }) {
                AnimatedVisibility(expanded, modifier = Modifier.align(alignment = Alignment.End)) {
                    Row {
                        IconButton(onClick = {
                            onUpdate(remind)
                        }) {
                            Icon(Icons.Default.Edit, null)
                        }
                        IconButton(onClick = {
                            expanded = !expanded
                            onDelete(remind)
                        }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                }
                MainInfo(remind)
                AnimatedVisibility(expanded) {
                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Row {
                            Text(MyString.type)
                            Text(remind.tab)
                        }
                        Row {
                            Text(MyString.create_time)
                            Text(remind.createTime)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MainInfo(remind: RemindEntity) {
    Row(
        Modifier.padding(top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center
        ) {
            if (maxWidth > 300.dp) {
                if (remind.image == null) {
                    Surface(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(70.dp),
                        shape = CircleShape,
                        color = Color(0xFFEEEEEE),
                        elevation = 10.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = if (remind.name.length >= 2) {
                                    remind.name.slice(IntRange(0, 1))
                                } else {
                                    remind.name
                                },
                                style = MyTypography.title
                            )
                        }
                    }
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .height(70.dp)
                            .width(70.dp)
                            .clip(CircleShape),
                        model = "${IMAGE_URL}${remind.image}",
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1.0f)
                .padding(end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = remind.expireTime,
                style = MyTypography.remind1,
            )
            Text(
                text = remind.name,
                style = MyTypography.remind2
            )
        }
    }
}
