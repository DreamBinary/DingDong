package com.glintcatcher.dingdong00.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Stable
object MyTypography {

    val title = TextStyle(
        fontWeight = FontWeight.W500,
        fontFamily = FontFamily.SansSerif,
        fontSize = 25.sp,
        letterSpacing = 5.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )
    val drawer = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 1.5.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )
    val setting1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 1.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )
    val sheet1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 1.5.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )
    val sheet2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 1.5.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )

    val remind1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        letterSpacing = 0.25.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )
    val remind2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 1.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )

    val sortTab = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 1.5.sp,
        textAlign = TextAlign.Start,
//        color = textColor
    )

    val bodySmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 15.sp,
        letterSpacing = 0.sp,
        textAlign = TextAlign.Center,
//        color = textColor
    )

    val bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 2.sp,
        textAlign = TextAlign.Justify,
    )
}