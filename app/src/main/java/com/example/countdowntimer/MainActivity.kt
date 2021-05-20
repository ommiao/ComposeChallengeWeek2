package com.example.countdowntimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.countdowntimer.ui.CountdownTimerViewModel
import com.example.countdowntimer.ui.InputScreen
import com.example.countdowntimer.ui.theme.Blue8A
import com.example.countdowntimer.ui.theme.CountdownScreen
import com.example.countdowntimer.ui.theme.CountdownTimerTheme
import com.example.countdowntimer.ui.theme.Dark20

class MainActivity : ComponentActivity() {

    enum class Page {
        InputScreen, CountdownScreen
    }

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CountdownTimerTheme {
                Box(modifier =
                Modifier
                    .background(Dark20)
                    .fillMaxSize()
                ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Screens()
                        BottomButton()
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomButton() {
        val viewModel: CountdownTimerViewModel = viewModel()
        FloatingActionButton(
            onClick = {
                viewModel.countdownOrStop()
            }, backgroundColor = Blue8A, contentColor = Color.Black
        ) {
            Icon(
                painterResource(
                    if (viewModel.page == Page.InputScreen)
                        R.drawable.ic_start
                    else
                        R.drawable.ic_stop
                ),
                "start"
            )
        }
    }

    @ExperimentalFoundationApi
    @Composable
    private fun Screens() {
        val viewModel: CountdownTimerViewModel = viewModel()
        val screen = viewModel.page
        Crossfade(
            targetState = screen,
            Modifier
                .padding(horizontal = 36.dp)
                .padding(top = 72.dp)
                .fillMaxWidth()
                .height(600.dp)
        ) {
            when (it) {
                Page.InputScreen -> {
                    InputScreen()
                }
                Page.CountdownScreen -> {
                    CountdownScreen()
                }
            }
        }
    }

}