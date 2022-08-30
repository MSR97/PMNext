package com.pomonext.pomonext.app

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.pomonext.pomonext.navigation.PomoNavigation
import com.pomonext.pomonext.screens.pomorun.PomoRunViewModel
import com.pomonext.pomonext.service.impl.PomoPunBroadCastReceiver
import com.pomonext.pomonext.ui.theme.PomoNextTheme
import com.pomonext.pomonext.utils.Constants
import com.pomonext.pomonext.utils.Constants.POMORUN_INTENT
import com.pomonext.pomonext.utils.Constants.POMORUN_NOTIFICATION_ACTION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private val pomoRunViewModel: PomoRunViewModel by viewModels<PomoRunViewModel>()
    private var timerReceiver: PomoPunBroadCastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pomoRunViewModel: PomoRunViewModel by viewModels()
        timerReceiver = PomoPunBroadCastReceiver(viewModel = pomoRunViewModel)
        setContent {
            PomoNextApp(pomoRunViewModel)

        }
    }

    override fun onStart() {
        super.onStart()
        if (timerReceiver != null) {
            if (!pomoRunViewModel.isReceiverRegistered.value) {
                val filter = IntentFilter(POMORUN_INTENT).apply {
                    addAction(POMORUN_NOTIFICATION_ACTION)
                }
                registerReceiver(timerReceiver, IntentFilter(filter))

                pomoRunViewModel.isReceiverRegistered.value = true

            }
        }
    }

    override fun onResume() {
        super.onResume()
        // register foreground service receiver if needed
        if (timerReceiver != null) {
            if (!pomoRunViewModel.isReceiverRegistered.value) {
                val filter = IntentFilter(POMORUN_INTENT).apply {
                    addAction(POMORUN_NOTIFICATION_ACTION)
                }
                registerReceiver(timerReceiver, IntentFilter(filter))
                pomoRunViewModel.isReceiverRegistered.value = true

            }
        }

    }


    override fun onPause() {
        super.onPause()
        // reset foreground service receiver if it's registered
//        if (timerReceiver != null) {
//            if (pomoRunViewModel.isReceiverRegistered.value) {
//                unregisterReceiver(timerReceiver)
//                pomoRunViewModel.isReceiverRegistered.value = false
//
//            }
//
//
//        }
    }


}


@Composable
fun PomoNextApp(pomoRunViewModel: PomoRunViewModel) {
    PomoNextTheme {
        val navController = rememberNavController()
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PomoNavigation(pomoRunViewModel)


            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PomoNextTheme {
    }
}