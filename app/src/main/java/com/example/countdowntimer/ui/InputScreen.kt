package com.example.countdowntimer.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdowntimer.ui.theme.Blue8A
import com.example.countdowntimer.ui.theme.Gray9A
import com.example.countdowntimer.ui.theme.bonava
import com.example.countdowntimer.ui.theme.product
import com.example.countdowntimer.R

@ExperimentalFoundationApi
@Composable
fun InputScreen() {
    Column(horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopTimeDisplay()
        Divider(
            color = Gray9A,
            modifier = Modifier
                .padding(top = 44.dp, bottom = 20.dp)
        )
        NumberKeyBoard()
    }
}

@ExperimentalFoundationApi
@Composable
fun NumberKeyBoard() {
    val viewModel: CountdownTimerViewModel = viewModel()
    LazyVerticalGrid(cells = GridCells.Fixed(3)) {
        items(viewModel.numbers) { number ->
            Box(modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickable(interactionSource = remember {
                    MutableInteractionSource()
                },
                    indication = if (number.isEmpty()) null else rememberRipple(color = Gray9A),
                    onClick = {
                        if (number.isNotEmpty()) {
                            viewModel.inputNumber(number)
                        }
                    }
                )
            ){
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    fontFamily = product,
                    text = number,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp
                )
            }
        }
    }
}

@Composable
private fun TopTimeDisplay() {
    val viewModel: CountdownTimerViewModel = viewModel()
    val fullNumbers = viewModel.fullNumbers
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(buildAnnotatedString {
            val colorTime = if(viewModel.inputNumbers.length > 0) Blue8A else Gray9A
            withStyle(style = SpanStyle(colorTime, fontSize = 52.sp)){
                append(fullNumbers.substring(0, 2))
            }
            withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                append("h    ")
            }
            withStyle(style = SpanStyle(colorTime, fontSize = 52.sp)){
                append(fullNumbers.substring(2, 4))
            }
            withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                append("m    ")
            }
            withStyle(style = SpanStyle(colorTime, fontSize = 52.sp)){
                append(fullNumbers.substring(4, 6))
            }
            withStyle(style = SpanStyle(colorTime, fontSize = 18.sp)){
                append("s   ")
            }
        }, fontFamily = bonava, textAlign = TextAlign.Center)
        Icon(
            painterResource(R.drawable.ic_remove),
            "remove",
            tint = Gray9A,
            modifier = Modifier.clickable {
                viewModel.removeNumber()
            }
        )
    }
}