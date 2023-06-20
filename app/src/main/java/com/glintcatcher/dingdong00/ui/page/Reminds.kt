package com.glintcatcher.dingdong00.ui.page


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.ui.component.RemindLazyColumn
import com.glintcatcher.dingdong00.ui.component.TabTimeRow
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel

@Composable
fun Reminds(viewModel: RemindViewModel, onUpdate: (RemindEntity) -> Unit) {
    Column {
        TabTimeRow(viewModel)
        RemindLazyColumn(
            viewModel.reminds.collectAsLazyPagingItems(),
            viewModel.refreshing,
            onUpdate = onUpdate,
            onDelete = { remind ->
                viewModel.deleteRemind(remind)
            }) { viewModel.refresh() }

    }

}
