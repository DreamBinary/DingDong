package com.glintcatcher.dingdong00.ui.page

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.glintcatcher.dingdong00.R
import com.glintcatcher.dingdong00.ui.component.AddTabDialog
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.ui.theme.MyTypography
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.utils.TimeSelect
import com.glintcatcher.dingdong00.viewmodel.InsertViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Sheet(
    insertViewModel: InsertViewModel,
    tabViewModel: TabViewModel,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    onScanClick: () -> Unit,
) {

    val showing = remember {
        mutableStateOf(0)
    }

    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }


    val context = LocalContext.current
    val uri: MutableState<Uri?> = remember {
        mutableStateOf(null)
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(uri.value).build()
    )
    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            it?.let {
                bitmap.value = it
                showing.value = 1
            }
        }
    )
    val pickPhoto =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = {
                it?.let {
                    uri.value = it
                    showing.value = 2
                }
            }
        )

    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it[Manifest.permission.READ_CALENDAR] == true && it[Manifest.permission.WRITE_CALENDAR] == true) {
                insertViewModel.insert(context, showing.value, bitmap.value, uri.value)
            }
            if (it[Manifest.permission.CAMERA] == true) {
                takePhoto.launch(null)
            }
            if (it[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                pickPhoto.launch(arrayOf("image/*"))
            }
        }
    )
    Column {
        Action(
            { permission.launch(arrayOf(Manifest.permission.CAMERA)) },
            { permission.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) },
            { scope.launch { scaffoldState.bottomSheetState.collapse() } })
        AnimatedVisibility(
            showing.value == 1,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(), contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.High
            )
        }

        AnimatedVisibility(
            showing.value == 2,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painter, contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
        }

        Date(insertViewModel.calendar, insertViewModel.date)
        Time(insertViewModel.calendar, insertViewModel.time)
        Good(
            insertViewModel.name,
            { insertViewModel.changeName(it) },
            { insertViewModel.changeName("") },
            onScanClick
        )
        RemindTab(insertViewModel.tab, tabViewModel) { insertViewModel.changeTab(it) }
        Confirm(insertViewModel.name.isNotBlank()) {
            scope.launch {
                scaffoldState.bottomSheetState.collapse()
                permission.launch(
                    arrayOf(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR
                    )
                )
            }
        }
    }
}

@Composable
fun Action(onTakePhoto: () -> Unit, onPickPhoto: () -> Unit, onCollapse: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onTakePhoto) {
            Icon(painterResource(R.drawable.camera), null, modifier = Modifier.size(20.dp))
        }

        IconButton(onClick = onPickPhoto) {
            Icon(painterResource(R.drawable.album), null, modifier = Modifier.size(20.dp))
        }

        IconButton(onClick = onCollapse) {
            Icon(Icons.Default.KeyboardArrowDown, null, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun Date(calendar: Calendar, date: MutableState<String>) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp)
    ) {
        Text(
            text = MyString.date,
            textAlign = TextAlign.Center,
            style = MyTypography.sheet1,
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = date.value,
            textAlign = TextAlign.Center,
            style = MyTypography.sheet2,
            modifier = Modifier
                .weight(7f)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = {
                TimeSelect.dateShow(context, calendar, date).show()
            },
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        ) {
            Icon(painterResource(R.drawable.date), contentDescription = null)
        }
    }
}

@Composable
private fun Time(calendar: Calendar, time: MutableState<String>) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp)
    ) {
        Text(
            text = MyString.time,
            textAlign = TextAlign.Center,
            style = MyTypography.sheet1,
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = time.value,
            textAlign = TextAlign.Center,
            style = MyTypography.sheet2,
            modifier = Modifier
                .weight(7f)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = {
                TimeSelect.timeShow(context, calendar, time).show()
            },
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        ) {
            Icon(painter = painterResource(id = R.drawable.time), contentDescription = null)
        }
    }
}

@Composable
private fun Good(
    text: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onScanClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp)
    ) {
        Text(
            text = MyString.good,
            textAlign = TextAlign.Center,
            style = MyTypography.sheet1,
            modifier = Modifier
                .weight(2f)
                .align(Alignment.CenterVertically)
        )
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            textStyle = MyTypography.sheet2,
            modifier = Modifier
                .weight(7f)
                .align(Alignment.CenterVertically),
            cursorBrush = SolidColor(MyTheme.colors.primary),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.BottomEnd) {
                    innerTextField()
                }
            },
            singleLine = true
        )
        AnimatedVisibility(text.isNotBlank()) {
            IconButton(
                onClick = onClearClick,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }

        IconButton(
            onClick = onScanClick,
            modifier = Modifier
                .weight(if (text.isNotBlank()) 1f else 2f)
                .align(Alignment.CenterVertically)
        ) {
            Icon(painter = painterResource(id = R.drawable.scan), contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RemindTab(
    tab: String, viewModel: TabViewModel, onChangeTab: (String) -> Unit,
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val add = viewModel.add

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {
            add.value = false
            expanded.value = false
        }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        ) {
            Text(
                text = MyString.tab,
                textAlign = TextAlign.Center,
                style = MyTypography.sheet1,
                modifier = Modifier
                    .weight(2f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = tab,
                textAlign = TextAlign.Center,
                style = MyTypography.sheet2,
                modifier = Modifier
                    .weight(7f)
                    .align(Alignment.CenterVertically)
            )
            Box(modifier = Modifier.weight(2f)) {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded.value
                ) {
                    expanded.value = !expanded.value
                }
            }

        }
        val tabs = viewModel.tabs.collectAsLazyPagingItems()
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            },
        ) {
            tabs.itemSnapshotList.items.forEach {
                DropdownMenuItem(
                    onClick = {
                        onChangeTab(it.name)
                        expanded.value = false
                    },
                    modifier = Modifier.height(50.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(viewModel.getIcon(it.name)),
                            contentDescription = null,
                            modifier = Modifier
                                .height(20.dp)
                                .width(50.dp),
                            tint = MyTheme.colors.primary
                        )
                        Text(
                            text = it.name,
                            style = MyTypography.sortTab.copy(fontSize = 15.sp),
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }
            }

            DropdownMenuItem(onClick = { add.value = true }, modifier = Modifier.height(50.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = MyString.add)
                Spacer(modifier = Modifier.weight(1f))
            }
            AddTabDialog(viewModel)
        }
    }
}


@Composable
fun Confirm(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MyTheme.colors.button,
        ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = MyString.btnConfirm)
    }
}