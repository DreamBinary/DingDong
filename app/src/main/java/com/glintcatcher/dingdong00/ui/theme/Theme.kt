package com.glintcatcher.dingdong00.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.glintcatcher.dingdong00.utils.theme.Theme
import com.glintcatcher.dingdong00.utils.theme.themeState


private val LocalMyColors = compositionLocalOf {
    when (themeState.value) {
        is Theme.ThemeDark ->
            Theme.ThemeDark.DarkColorPalette
        is Theme.Theme0 ->
            Theme.Theme0.LightColorPalette
        is Theme.Theme1 ->
            Theme.Theme1.LightColorPalette
        is Theme.Theme2 ->
            Theme.Theme2.LightColorPalette
        is Theme.Theme3 ->
            Theme.Theme3.LightColorPalette
        is Theme.Theme4 ->
            Theme.Theme4.LightColorPalette
        is Theme.Theme5 ->
            Theme.Theme5.LightColorPalette
    }
}

@Stable
object MyTheme {
    val colors: MyColors
        @Composable
        get() = LocalMyColors.current
}


@Composable
fun MyTheme(
    theme: Theme = themeState.value,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val targetColors =
        if (darkTheme) {
            Theme.ThemeDark.DarkColorPalette
        } else {
            when (theme) {
                is Theme.Theme0 ->
                    Theme.Theme0.LightColorPalette
                is Theme.Theme1 ->
                    Theme.Theme1.LightColorPalette
                is Theme.Theme2 ->
                    Theme.Theme2.LightColorPalette
                is Theme.Theme3 ->
                    Theme.Theme3.LightColorPalette
                is Theme.Theme4 ->
                    Theme.Theme4.LightColorPalette
                is Theme.Theme5 ->
                    Theme.Theme5.LightColorPalette
                else -> Theme.Theme0.LightColorPalette
            }
        }

    val colors = MyColors(
        primary = targetColors.primary,
        background = if (darkTheme) darkBackground else Color.White,
        topBar = targetColors.topBar,
        bottomBar = targetColors.bottomBar,
        drawerHeader = targetColors.drawerHeader,
        item1 = targetColors.item1,
        floatingBtn = targetColors.floatingBtn,
        tab = targetColors.tab,
        sortBackground = targetColors.sortBackground,
        sheetBackground = targetColors.sheetBackground,
        button = targetColors.button,
        stroke = targetColors.stroke,
    )

    CompositionLocalProvider(
        LocalMyColors provides colors,
        content = content
    )
}

