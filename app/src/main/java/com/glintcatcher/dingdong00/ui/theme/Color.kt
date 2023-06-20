package com.glintcatcher.dingdong00.ui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val sheet1 = Color(0xffF8F8F8)
val sheet2 = Color(0xff6C6D6E)

val top0 = Color(0xffa4cfec)
val bottom0 = Color(0xffa4cfec)
val primary0 = Color(0xff43a6e8)
val secondary0 = Color(0xffa4cfec)
val stroke0 = Color(0xff1390e4)
val tab0 = Color(0xff1390e4)
val floatingBtn0 = Color(0xff1492E6)

val top1 = Color(0xffEA9B64)
val bottom1 = Color(0xffF5F5F5)
val primary1 = Color(0xffFF8936)
val secondary1 = Color(0xffE89961)
val stroke1 = Color(0xffFF8936)
val tab1 = Color(0xffFF8936)

val top2 = Color(0xffF7F0A0)
val bottom2 = Color(0xffF5F5F5)
val primary2 = Color(0xffEFE130)
val secondary2 = Color(0xffF7F0A0)
val stroke2 = Color(0xffEFE130)
val tab2 = Color(0xffEFE130)

val top3 = Color(0xff8FE7BC)
val bottom3 = Color(0xffF5F5F5)
val primary3 = Color(0xff59C28E)
val secondary3 = Color(0xffB0DDC7)
val stroke3 = Color(0xff59C28E)
val tab3 = Color(0xff59C28E)

val top4 = Color(0xffEC7674)
val bottom4 = Color(0xffF5F5F5)
val primary4 = Color(0xffDD504E)
val secondary4 = Color(0xffE9AEAE)
val stroke4 = Color(0xffDD504E)
val tab4 = Color(0xffDD504E)

val top5 = Color(0xffC283DF)
val bottom5 = Color(0xffF5F5F5)
val primary5 = Color(0xff844A9F)
val secondary5 = Color(0xffC7AED3)
val stroke5 = Color(0xff844A9F)
val tab5 = Color(0xff844A9F)

val topDark = Color(0xff1C1C1E)
val bottomDark = Color(0xff1C1C1E)
val primaryDark = Color(0xff43A6E8)
val secondaryDark = Color(0xff6DB3E5)
val strokeDark = Color(0xff49A7E5)
val tabDark = Color(0xff49A7E5)

val darkBackground = Color(0xff1C1C1E)
@Stable
class MyColors(
    primary: Color,
    topBar: Color,
    bottomBar: Color,
    drawerHeader: Color,
    item1: Color,
    floatingBtn: Color,
    tab: Color,
    sortBackground: Color,
    sheetBackground: Color,
    button: Color,
    stroke: Color,
    background: Color = Color.White,
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var topBar by mutableStateOf(topBar, structuralEqualityPolicy())
        internal set
    var bottomBar by mutableStateOf(bottomBar, structuralEqualityPolicy())
        internal set
    var drawerHeader by mutableStateOf(drawerHeader, structuralEqualityPolicy())
        internal set
    var item1 by mutableStateOf(item1, structuralEqualityPolicy())
        internal set
    var floatingBtn by mutableStateOf(floatingBtn, structuralEqualityPolicy())
        internal set
    var tab by mutableStateOf(tab, structuralEqualityPolicy())
        internal set
    var sortBackground by mutableStateOf(sortBackground, structuralEqualityPolicy())
        internal set
    var sheetBackground by mutableStateOf(sheetBackground, structuralEqualityPolicy())
        internal set
    var button by mutableStateOf(button, structuralEqualityPolicy())
        internal set
    var stroke by mutableStateOf(stroke, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
//    var text by mutableStateOf(text, structuralEqualityPolicy())
//        internal set
}