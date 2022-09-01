package com.pomonext.pomonext.screens.pomorun

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.pomonext.pomonext.app.PomoNextApplication
import com.pomonext.pomonext.helper.minToSecond
import com.pomonext.pomonext.helper.secondsToTime
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.service.PomoRunForeGroundService
import com.pomonext.pomonext.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PomoRunViewModel @Inject constructor(
    private val pomoRunService: PomoRunForeGroundService,
) : ViewModel() {
    private val focusRunningTimeValue = mutableStateOf(5)
    private val breakRunningTimeValue = mutableStateOf(2)
    val timerCurrentValue = mutableStateOf(focusRunningTimeValue.value)
    val isPomoRunning = mutableStateOf(false)
    val isPomoPaused = mutableStateOf(false)
    val isForeGroundServiceRunning = mutableStateOf(false)
    val isReceiverRegistered = mutableStateOf(false)


    private fun getPomoRunService(): PomoRunForeGroundService {
        pomoRunService.setTemplateTime(
//            focusTimeInput = focusRunningTimeValue.value.minToSecond(),
//            breakTimeInput = breakRunningTimeValue.value.minToSecond()
            focusTimeInput = focusRunningTimeValue.value,
            breakTimeInput = breakRunningTimeValue.value
        )
        return pomoRunService
    }


    fun startPomoRun(context: Context, viewModel: PomoRunViewModel) {
        sendCommandToForegroundService(PomoRunState.START, context = context)
    }


    fun stopPomoRun(context: Context) {
        sendCommandToForegroundService(PomoRunState.STOP, context = context)
    }

    fun pausePomoRun(context: Context) {
        sendCommandToForegroundService(PomoRunState.PAUSE, context = context)
    }


    fun resume(context: Context) {
        sendCommandToForegroundService(PomoRunState.Resume, context = context)
    }


    private fun getServiceIntent(
        command: PomoRunState,
        context: Context,
    ) =
        Intent(context, getPomoRunService()::class.java).apply {
            putExtra(Constants.SERVICE_COMMAND, command as Parcelable)
        }

    private fun sendCommandToForegroundService(
        timerState: PomoRunState,
        context: Context
    ) {
        ContextCompat.startForegroundService(
            context,
            getServiceIntent(timerState, context)
        )
        isPomoRunning.value = timerState != PomoRunState.STOP

    }


}