package com.glintcatcher.dingdong00.ui.page


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.ui.component.RemindLazyColumn
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel


@Composable
fun TimeOut(viewModel: RemindViewModel, onUpdate: (RemindEntity) -> Unit) {
    val list = viewModel.timeout.collectAsLazyPagingItems().itemSnapshotList.items
    Column {
        TextButton(
            onClick = {
                viewModel.deleteList(list)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp),
            colors = ButtonDefaults.buttonColors(MyTheme.colors.button),
            elevation = ButtonDefaults.elevation(20.dp)
        ) {
            Text(text = "清空过时")
        }
        RemindLazyColumn(
            viewModel.timeout.collectAsLazyPagingItems(),
            viewModel.refreshing,
            onUpdate = onUpdate,
            onDelete = { remind ->
                viewModel.deleteRemind(remind)
            }) { viewModel.findTimeout() }
    }
}


