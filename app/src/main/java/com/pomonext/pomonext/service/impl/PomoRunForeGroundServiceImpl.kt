package com.pomonext.pomonext.service.impl

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.compose.ui.input.key.Key.Companion.K
import com.pomonext.pomonext.R
import com.pomonext.pomonext.helper.PomoRunNotificationHelper
import com.pomonext.pomonext.helper.secondsToTime
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.service.PomoRunForeGroundService
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_PAUSED
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_RUNNING
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_TEXT
import com.pomonext.pomonext.utils.Constants.POMORUN_INTENT
import com.pomonext.pomonext.utils.Constants.SERVICE_COMMAND
import com.pomonext.pomonext.utils.logC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private var selectedTime = 0
private var currentTime: Int = 0
private var totalTime: Int = 0
private var isFinished: Boolean = false
private var isItRunning: Boolean = false
private var isItPaused: Boolean = false

class PomoRunForeGroundServiceImpl @Inject constructor() : PomoRunForeGroundService, Service(),
    CoroutineScope {

    var serviceState: PomoRunState = PomoRunState.INITIALIZED
    private val notificationHelper by lazy { PomoRunNotificationHelper(this) }
    private val handler = Handler(Looper.getMainLooper())

    private var runnable: Runnable = object : Runnable {
        override fun run() {

            currentTime++

            broadcastUpdate()
            // Repeat every 1 second
            handler.postDelayed(this, 1000)
        }

    }
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
                PomoRunState.STOP -> endTimerService()
                else -> return Service.START_NOT_STICKY
            }
            notificationHelper.intent = intent

        }





        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        job.cancel()
    }

    private fun startTimer() {
        serviceState = PomoRunState.START

        // publish notification
        startForeground(
            PomoRunNotificationHelper.NOTIFICATION_ID,K
            notificationHelper.getNotification()
        )
        isItRunning = true
        isItPaused = false
        broadcastUpdate()

        startCoroutineTimer()
    }

    private fun broadcastUpdate() {
        // count elapsed time
        val remainingTime = (totalTime - currentTime)
        if(!isFinished){

        logC(remainingTime.toString())
        if (remainingTime <= 0) {
            isFinished = true
            Toast.makeText(applicationContext, "a", Toast.LENGTH_SHORT).show()
            endTimerService()


        }


        // update notification
        when (serviceState) {
            PomoRunState.START -> {

                // send time to update UI
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime)
                        .putExtra(NOTIFICATION_TIMER_IS_RUNNING, isItRunning)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused)

                )

                notificationHelper.updateNotification(
                    getString(R.string.time_is_running, remainingTime.secondsToTime())
                )
            }
            PomoRunState.PAUSE -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime)
                        .putExtra(NOTIFICATION_TIMER_IS_PAUSED, isItPaused)
                )


            }
            PomoRunState.STOP -> {
                notificationHelper.updateNotification(getString(R.string.get_back))
                sendBroadcast(
                    Intent(POMORUN_INTENT)
                        .putExtra(NOTIFICATION_TIMER_TEXT, remainingTime)
                )
            }
            else -> {}
        }
    }
    }

    private fun pauseTimerService() {
        serviceState = PomoRunState.PAUSE
        handler.removeCallbacks(runnable)
        isItRunning = false
        isItPaused = true
        broadcastUpdate()

    }

    private fun endTimerService() {
        if (isItRunning || isItPaused) {
            currentTime = 0
            serviceState = PomoRunState.STOP
            handler.removeCallbacks(runnable)
            isItRunning = false
            isItPaused = false
            broadcastUpdate()
            stopService()
        }
    }

    private fun stopService() {
        stopForeground(true)
    }

    private fun startCoroutineTimer() {
        launch(coroutineContext) {
            handler.post(runnable)

        }
    }

    override fun setTemplateTime(focusTime: Int, breakTime: Int) {
        totalTime = focusTime
        selectedTime = breakTime
    }


}

