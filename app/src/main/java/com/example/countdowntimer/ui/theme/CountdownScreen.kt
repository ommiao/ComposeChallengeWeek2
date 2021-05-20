package com.example.countdowntimer.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdowntimer.ui.CountdownTimerViewModel
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun CountdownScreen() {
    val viewModel: CountdownTimerViewModel = viewModel()
    val angle = animateFloatAsState(
        targetValue = if (viewModel.countdownSeconds == 0) 0f else 360f,
        TweenSpec(durationMillis = viewModel.countdownSeconds * 1000, easing = LinearEasing),
        finishedListener = {
            if(it == 360f){
                viewModel.preStop()
            } else if (it == 0f){
                viewModel.stop()
            }
        }
    )
    if(!viewModel.animateFinished){
        viewModel.animateNow()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .size(240.dp)
            .align(Alignment.Center)
            .padding(10.dp)
            .drawWithContent {
                drawIntoCanvas { canvas ->
                    drawCircle(
                        color = Color.White,
                        radius = 110.dp.toPx(),
                        style = Stroke(width = 5.dp.toPx())
                    )
                    val x = center.x + sin(
                        Math
                            .toRadians(angle.value.toDouble())
                            .toFloat()
                    ) * 110.dp.toPx()
                    val y = center.y - cos(
                        Math
                            .toRadians(angle.value.toDouble())
                            .toFloat()
                    ) * 110.dp.toPx()
                    drawCircle(
                        color = Blue8A,
                        radius = 10.dp.toPx(),
                        center = Offset(x, y)
                    )
                    drawArc(
                        color = Blue8A,
                        startAngle = -90f,
                        sweepAngle = angle.value,
                        style = Stroke(width = 5.dp.toPx()),
                        useCenter = false
                    )
                }
                drawContent()
            }
        ) {
            Text(buildAnnotatedString {
                val colorTime = Color.White
                val seconds = Math.ceil((viewModel.countdownSeconds * (360 - angle.value) / 360).toDouble()).toInt()
                val hour = seconds / 3600
                val minute = (seconds - hour * 3600) / 60
                val second = seconds % 60
                withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)){
                    append(String.format("%02d", hour))
                }
                withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                    append("h  ")
                }
                withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)){
                    append(String.format("%02d", minute))
                }
                withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                    append("m  ")
                }
                withStyle(style = SpanStyle(colorTime, fontSize = 36.sp)){
                    append(String.format("%02d", second))
                }
                withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                    append("s")
                }
            }, fontFamily = bonava, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
        }
    }
}