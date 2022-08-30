package com.pomonext.pomonext.screens.pomorun

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.pomonext.pomonext.app.PomoNextApplication
import com.pomonext.pomonext.helper.minToSecond
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.model.PomoRunType
import com.pomonext.pomonext.service.PomoRunForeGroundService
import com.pomonext.pomonext.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PomoRunViewModel @Inject constructor(
    private val pomoRunService: PomoRunForeGroundService,
    private val app: PomoNextApplication
) : AndroidViewModel(app) {
    private val focusRunningTimeValue = mutableStateOf(10)
    private val breakRunningTimeValue = mutableStateOf(5)
    private val serviceRunningTimeValue = mutableStateOf(5)

    val isReceiverRegistered = mutableStateOf(false)
    val isPomoRunning = mutableStateOf(false)
    val isPomoPaused = mutableStateOf(false)

    val timerCurrentValue = mutableStateOf(focusRunningTimeValue.value.minToSecond())





    private fun getPomoRunService(): PomoRunForeGroundService {
        pomoRunService.setTemplateTime(
            focusTime = focusRunningTimeValue.value,
            breakTime = breakRunningTimeValue.value
        )
        return pomoRunService
    }


    fun startPomoRun(context: Context, viewModel: PomoRunViewModel) {
        sendCommandToForegroundService(PomoRunState.START, context = context)
        viewModel.isPomoRunning.value = true
        viewModel.isPomoPaused.value = false

    }

    fun stopPomoRun(context: Context) {
        sendCommandToForegroundService(PomoRunState.STOP, context = context)
        isPomoRunning.value = false
        isPomoPaused.value = false


    }

    fun pausePomoRun(context: Context) {
        sendCommandToForegroundService(PomoRunState.PAUSE, context = context)
        isPomoRunning.value = false
        isPomoPaused.value = true


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