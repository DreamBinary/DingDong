package com.glintcatcher.dingdong00.ui.component
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.glintcatcher.dingdong00.utils.MMKVUtil
import com.glintcatcher.dingdong00.utils.theme.Theme
import com.glintcatcher.dingdong00.utils.theme.themeList

@Composable
fun ThemePicker(
    primaryColorList: List<Color>,
    showTheme: MutableState<Theme>,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        content = { ThemePickerContent(primaryColorList, showTheme) }
    )
}

@androidx.compose.runtime.Composable
private fun ThemePickerContent(
    primaryColorList: List<Color>,
    showTheme: MutableState<Theme>,
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        elevation = 10.dp
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            itemsIndexed(primaryColorList) { index, item ->
                Surface(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .clickable {
                            MMKVUtil.put("theme", index)
                            showTheme.value = themeList[index]
                        },
                    shape = CircleShape,
                    color = item,
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    if (showTheme.value == themeList[index]) {
                        Icon(Icons.Default.Done, null, modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}