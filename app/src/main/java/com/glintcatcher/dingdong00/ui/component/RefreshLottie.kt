package com.glintcatcher.dingdong00.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.glintcatcher.dingdong00.R
import com.zj.refreshlayout.SwipeRefreshState

@Composable
fun LottieHeaderTwo(state: SwipeRefreshState) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    val speed by remember {
        mutableStateOf(1f)
    }
    isPlaying = state.isRefreshing
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.refresh),
    )
    val lottieAnimationState by animateLottieCompositionAsState(
        composition = lottieComposition, // 动画资源句柄
        iterations = LottieConstants.IterateForever, // 迭代次数
        isPlaying = isPlaying, // 动画播放状态
        speed = speed, // 动画速度状态
        restartOnPlay = false // 暂停后重新播放是否从头开始
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            lottieComposition,
            lottieAnimationState,
            modifier = Modifier
                .padding(0.dp, 5.dp)
                .size(100.dp)
        )

    }

}