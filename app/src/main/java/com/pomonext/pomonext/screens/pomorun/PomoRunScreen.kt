package com.pomonext.pomonext.screens.pomorun

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pomonext.pomonext.R
import com.pomonext.pomonext.helper.secondsToTime
import com.pomonext.pomonext.model.PomoRunType
import com.pomonext.pomonext.ui.theme.PomoRunC
import com.pomonext.pomonext.widgets.PomoAppBar
import com.pomonext.pomonext.widgets.PomoBottomNavigationBar

@Composable
fun PomoRunScreen(navController: NavHostController, viewModel: PomoRunViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Scaffold(
        topBar = { PomoAppBar(navController) },
        bottomBar = { PomoBottomNavigationBar(navController = navController) }

    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = Color.White
        ) {
            //PomoRunContent
            PomoRunContent(navController, context, viewModel)
//            viewModel.setUpPomoRun()


            ///////////////////////


        }

    }


}


@Composable
fun PomoRunContent(
    navController: NavHostController,
    context: Context,
    viewModel: PomoRunViewModel
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        backgroundColor = PomoRunC,
        shape = RoundedCornerShape(20.dp),
    ) {
        PomoInfo()
        PomoSettingButton()
        PomoTimer(context = context, viewModel)
//        Text(text = viewModel.timerText.value)


    }
}


@Composable
private fun PomoInfo() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Project A",
            textAlign = TextAlign.Start,
            fontSize = 12.28125.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(80.dp)
                .alpha(1f)
                .padding(start = 10.dp, top = 10.dp),
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )
        Text(
            text = "Task number",
            textAlign = TextAlign.Start,
            fontSize = 16.375.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(200.dp)
                .alpha(1f)
                .padding(start = 10.dp),
            color = Color(red = 1f, green = 1f, blue = 1f, alpha = 1f),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        )


    }
}

@Composable
fun PomoSettingButton() {
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.End) {

        IconButton(
            modifier = Modifier
                .size(58.dp),
            onClick = { Log.d("TestButton", "Pressed") },

            ) {
            Image(
                painter = painterResource(id = R.drawable.pomosetting),
                contentDescription = "Setting"
            )

        }

    }
}


@Composable
fun PomoTimer(context: Context, viewModel: PomoRunViewModel) {
    val timerValue = rememberSaveable {
        mutableStateOf("")
    }
    if (viewModel.timerCurrentValue.value <= 0) {
        timerValue.value = "Break Time"
//        viewModel.stopPomoRun(context)
    } else {
        timerValue.value = viewModel.timerCurrentValue.value.secondsToTime()
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val haptic = LocalHapticFeedback.current

        Text(
            text = timerValue.value,
            textAlign = TextAlign.Center,
            fontSize = 100.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .alpha(1f),
            color = Color.White,
            fontWeight = FontWeight.Light,
            fontStyle = FontStyle.Normal,
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier
                    .size(58.dp),
                onClick = {
                    if (!viewModel.isPomoRunning.value) {
                        viewModel.startPomoRun(context = context, viewModel = viewModel)
                    } else {
                        viewModel.pausePomoRun(context = context)
                    }
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                },

                ) {
                Image(
                    painter = painterResource(
                        id = if (!viewModel.isPomoRunning.value)
                            R.drawable.playbutton else R.drawable.pausebutton
                    ),
                    contentDescription = "Start"
                )
            }
            IconButton(
                modifier = Modifier
                    .size(58.dp),
                onClick = {
                    timerValue.value = viewModel.timerCurrentValue.value.secondsToTime()
                    viewModel.stopPomoRun(context = context)
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                },
                enabled = (viewModel.isPomoRunning.value || viewModel.isPomoPaused.value)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.stopbutton),
                    contentDescription = "Pause",
                )
            }


        }

    }
}
