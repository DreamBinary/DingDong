package com.glintcatcher.dingdong00.ui.component

/**
 * @author CXQ
 * @date 2022/5/26
 */
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.Constans.MyString
import com.glintcatcher.dingdong00.viewmodel.TabViewModel

@Composable
fun AddTabDialog(viewModel: TabViewModel) {
    val add = viewModel.add
    val newTab = viewModel.newTab.value
    AnimatedVisibility(add.value) {
        AlertDialog(
            onDismissRequest = { add.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.insertTab()
                        add.value = false
                    },
                ) { Text(MyString.add, color = Color.Black) }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        add.value = false
                    },
                ) {
                    Text(MyString.btnCancel, color = Color.Black)
                }
            },
            icon = {
                Icon(Icons.Default.AddCircle, contentDescription = null)
            },
            title = {
                Text(MyString.title)
            },
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = MyString.add_dot, modifier = Modifier.padding(end = 10.dp))
                    OutlinedTextField(
                        value = newTab.name, onValueChange = { viewModel.changeNewTabName(it) },
                        modifier = Modifier
                            .weight(7f)
                            .align(Alignment.CenterVertically),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(viewModel.getIcon(newTab.name)),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(50.dp),
                                tint = MyTheme.colors.primary
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.insertTab()
                                add.value = false
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Done,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(50.dp),
                                    tint = MyTheme.colors.primary
                                )
                            }
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            cursorColor = MyTheme.colors.primary,
                            focusedBorderColor =
                            MyTheme.colors.primary.copy(alpha = ContentAlpha.medium),
                            backgroundColor = Color.Transparent
                        )
                    )
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
