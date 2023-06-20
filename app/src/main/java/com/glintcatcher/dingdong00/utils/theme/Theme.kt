package com.glintcatcher.dingdong00.utils.theme

import com.glintcatcher.dingdong00.ui.theme.*

sealed class Theme {
    object ThemeDark: Theme() {
        val DarkColorPalette = MyColors(
            primary = primaryDark,
            topBar = topDark,
            bottomBar = bottomDark,
            drawerHeader = topDark,
            item1 = primaryDark,
            floatingBtn = secondaryDark,
            tab = tabDark,
            sortBackground = secondaryDark,
            sheetBackground = sheet2,
            button = secondaryDark,
            stroke = strokeDark
        )
    }
    object Theme0 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary0,
            topBar = top0,
            bottomBar = bottom0,
            drawerHeader = top0,
            item1 = primary0,
            floatingBtn = floatingBtn0,
            tab = tab0,
            sortBackground = secondary0,
            sheetBackground = sheet1,
            button = secondary0,
            stroke = stroke0
        )
    }

    object Theme1 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary1,
            topBar = top1,
            bottomBar = bottom1,
            drawerHeader = top1,
            item1 = primary1,
            floatingBtn = secondary1,
            tab = tab1,
            sortBackground = secondary1,
            sheetBackground = sheet1,
            button = secondary1,
            stroke = stroke1
        )
    }

    object Theme2 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary2,
            topBar = top2,
            bottomBar = bottom2,
            drawerHeader = top2,
            item1 = primary2,
            floatingBtn = secondary2,
            tab = tab2,
            sortBackground = secondary2,
            sheetBackground = sheet1,
            button = secondary2,
            stroke = stroke2
        )
    }

    object Theme3 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary3,
            topBar = top3,
            bottomBar = bottom3,
            drawerHeader = top3,
            item1 = primary3,
            floatingBtn = secondary3,
            tab = tab3,
            sortBackground = secondary3,
            sheetBackground = sheet1,
            button = secondary3,
            stroke = stroke3
        )
    }

    object Theme4 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary4,
            topBar = top4,
            bottomBar = bottom4,
            drawerHeader = top4,
            item1 = primary4,
            floatingBtn = secondary4,
            tab = tab4,
            sortBackground = secondary4,
            sheetBackground = sheet1,
            button = secondary4,
            stroke = stroke4
        )
    }

    object Theme5 : Theme() {
        val LightColorPalette = MyColors(
            primary = primary5,
            topBar = top5,
            bottomBar = bottom5,
            drawerHeader = top5,
            item1 = primary5,
            floatingBtn = secondary5,
            tab = tab5,
            sortBackground = secondary5,
            sheetBackground = sheet1,
            button = secondary5,
            stroke = stroke5
        )
    }
}
