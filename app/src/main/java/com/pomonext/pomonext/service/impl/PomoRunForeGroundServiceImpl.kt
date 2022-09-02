package com.pomonext.pomonext.service.impl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.mutableStateOf
import com.pomonext.pomonext.R
import com.pomonext.pomonext.helper.PomoRunNotificationHelper
import com.pomonext.pomonext.helper.secondsToTime
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.model.PomoRunType
import com.pomonext.pomonext.service.PomoRunForeGroundService
import com.pomonext.pomonext.utils.Constants.IS_FOREGROUND_SERVICE_RUNNING
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_PAUSED
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_RUNNING
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_TEXT
import com.pomonext.pomonext.utils.Constants.POMORUN_INTENT
import com.pomonext.pomonext.utils.Constants.SERVICE_COMMAND
import com.pomonext.pomonext.utils.logC
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


private var currentTime: Int = 0
private val isItRunning = mutableStateOf(false)
private val isItPaused = mutableStateOf(false)
private var isServiceOn = false
private var pomoRunType: PomoRunType = PomoRunType.Focus
private var focusTime: Int = 0
private var breakTime: Int = 0
private var totalTime: Int = focusTime
val remainingTime = mutableStateOf(totalTime - focusTime)
var previousSecondStampedTime: Int = 0


class PomoRunForeGroundServiceImpl @Inject constructor() : PomoRunForeGroundService, Service(),
    CoroutineScope {

    private var serviceState: PomoRunState = PomoRunState.INITIALIZED
    private val notificationHelper by lazy { PomoRunNotificationHelper(this) }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.extras?.run {
            when (getSerializable(SERVICE_COMMAND) as PomoRunState) {
                PomoRunState.START -> startTimer()
                PomoRunState.PAUSE -> pauseTimerService()
                PomoRunState.Resume -> resumeTimerService()
                PomoRunState.STOP -> stopTimerService()
                else -> return Service.START_NOT_STICKY
            }
            notificationHelper.intent = intent
        }
        return Service.START_NOT_STICKY
    }


    private fun broadcastUpdate() {
        // update notification
        when (serviceState) {
            PomoRunState.START -> {
                // send time to update UI
                notificationHelper.updateNotification(
                    getString(R.string.time_is_running, remainingTime.value.secondsToTime())
                )
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime.value)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning.value)
                        .putExtra(IS_FOREGROUND_SERVICE_RUNNING, isServiceOn)

                )
            }
            PomoRunState.PAUSE -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime.value)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning.value)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused.value)
                )
            }
            PomoRunState.Resume -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime.value)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning.value)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused.value)
                )
            }
            PomoRunState.STOP -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime.value)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning.value)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused.value)
                )
            }
            PomoRunState.Finished -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime.value)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning.value)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused.value)
                )
            }
            else -> {}
        }
    }

    private fun setUpPomodoroType(): Int {
        var inputToTimer: Int = focusTime
        if (serviceState == PomoRunState.Finished) {

            currentTime = 0

            if (pomoRunType == PomoRunType.Focus) {
                inputToTimer = breakTime
                pomoRunType = PomoRunType.Break

            } else if (pomoRunType == PomoRunType.Break) {
                inputToTimer = focusTime
                pomoRunType = PomoRunType.Focus

            }
        }

        return inputToTimer

    }

    private fun startTimer() {
        previousSecondStampedTime = LocalDateTime.now().second
        totalTime = setUpPomodoroType()
        remainingTime.value = totalTime - currentTime
        broadcastUpdate()

        if (!isServiceOn) {
            startForeground(
                PomoRunNotificationHelper.NOTIFICATION_ID,
                notificationHelper.getNotification()
            )
            isServiceOn = true
        }




        serviceState = PomoRunState.START

        isItRunning.value = true
        isItPaused.value = false
        broadcastUpdate()
        startCoroutineTimer()


    }


    private fun pauseTimerService() {
        isItRunning.value = false
        isItPaused.value = true
        broadcastUpdate()
        serviceState = PomoRunState.PAUSE
        coroutineContext.cancelChildren()

    }

    private fun resumeTimerService() {
//        serviceState = PomoRunState.Resume
        startTimer()
    }

    private fun stopTimerService() {
        if (isItRunning.value || isItPaused.value) {
            currentTime = 0
            remainingTime.value = totalTime
            serviceState = PomoRunState.STOP
            isItRunning.value = false
            isItPaused.value = false
            broadcastUpdate()
            stopService()
        }
    }

    private fun stopService() {
        coroutineContext.cancelChildren()
        isServiceOn = false
        stopForeground(true)

    }


    private fun tickerFlow() = flow {
        while (isItRunning.value) {
            emit(Unit)
            delay(1000)
        }
    }

    private fun startCoroutineTimer() {
        launch(coroutineContext) {
            tickerFlow()
                .map { LocalDateTime.now() }
                .distinctUntilChanged { old, new ->
                    new.second == old.second
                }
                .onEach {
                    logC(it.second.toString(), "TimeDiff")
                    if ((LocalDateTime.now().second) - 1 >= previousSecondStampedTime) {
                        logC(LocalDateTime.now().second.toString(), "LOCAL")
                        timerLogic()

                    }

                }
                .launchIn(this)


        }

    }

    private fun timerLogic() {
        if (serviceState != PomoRunState.Finished) {
            currentTime++
            remainingTime.value = (totalTime - currentTime)
            if (currentTime > totalTime) {
                pomoRunFinished()
            }
        }
        broadcastUpdate()
    }


    private fun pomoRunFinished() {
        serviceState = PomoRunState.Finished
        currentTime = 0
        isItRunning.value = false
        isItPaused.value = false
        coroutineContext.cancelChildren()
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun setTemplateTime(focusTimeInput: Int, breakTimeInput: Int) {
        totalTime = focusTime
        focusTime = focusTimeInput
        breakTime = breakTimeInput

    }


}

