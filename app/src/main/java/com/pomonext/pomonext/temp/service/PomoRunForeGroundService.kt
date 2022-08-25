//package com.pomonext.pomonext.temp.service
//
//import android.app.*
//import android.app.Service.START_NOT_STICKY
//import android.content.Intent
//import android.os.Build
//import android.os.Handler
//import android.os.IBinder
//import android.os.Looper
//import androidx.core.app.NotificationCompat
//import androidx.core.app.ServiceCompat.stopForeground
//import com.pomonext.pomonext.R
//import com.pomonext.pomonext.app.MainActivity
//import com.pomonext.pomonext.helper.NotificationHelper
//import com.pomonext.pomonext.model.PomoRunState
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import kotlin.coroutines.CoroutineContext
//
//const val SERVICE_COMMAND = "Command"
//const val NOTIFICATION_TEXT = "NotificationText"
//class PomoRunForeGroundService: Service(), CoroutineScope {
//
//    var serviceState: PomoRunState = PomoRunState.INITIALIZED
//    private val helper by lazy { NotificationHelper(this) }
//    private var currentTime: Int = 0
//    private var startedAtTimestamp: Int = 0
//    set(value) {
//        currentTime = value
//        field = value
//    }
//
//    private val handler = Handler(Looper.getMainLooper())
//    private var runnable: Runnable = object : Runnable {
//        override fun run() {
//            currentTime++
//            broadcastUpdate()
//            // Repeat every 1 second
//            handler.postDelayed(this, 1000)
//        }
//    }
//    private val job = Job()
//    override val coroutineContext: CoroutineContext
//    get() = Dispatchers.IO + job
//
//    override fun onBind(intent: Intent): IBinder? = null
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        super.onStartCommand(intent, flags, startId)
//
//        intent?.extras?.run {
//            when (getSerializable(SERVICE_COMMAND) as PomoRunState) {
//                PomoRunState.START -> startTimer()
//                PomoRunState.PAUSE -> pauseTimerService()
//                PomoRunState.STOP -> endTimerService()
//                else -> return START_NOT_STICKY
//            }
//        }
//        return START_NOT_STICKY
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        handler.removeCallbacks(runnable)
//        job.cancel()
//    }
//
//    private fun startTimer() {
//        serviceState = PomoRunState.START
//
//        startedAtTimestamp = 0
//
//        // publish notification
//        startForeground(NotificationHelper.NOTIFICATION_ID, helper.getNotification())
//        broadcastUpdate()
//
//        startCoroutineTimer()
//    }
//
//    private fun broadcastUpdate() {
//        // update notification
//        if (serviceState == PomoRunState.START) {
//            // count elapsed time
//            val elapsedTime = (currentTime - startedAtTimestamp)
//
//            // send time to update UI
//            sendBroadcast(
//                Intent(TIMER_ACTION)
//                    .putExtra(NOTIFICATION_TEXT, elapsedTime)
//            )
//
//            helper.updateNotification(
//                getString(R.string.time_is_running, elapsedTime.secondsToTime())
//            )
//        } else if (serviceState == PomoRunState.PAUSE) {
//            helper.updateNotification(getString(R.string.get_back))
//        }
//    }
//
//    private fun pauseTimerService() {
//        serviceState = PomoRunState.PAUSE
//        handler.removeCallbacks(runnable)
//        broadcastUpdate()
//    }
//
//    private fun endTimerService() {
//        serviceState = PomoRunState.STOP
//        handler.removeCallbacks(runnable)
//        broadcastUpdate()
//        stopService()
//    }
//
//    private fun stopService() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            stopForeground(true)
//        } else {
//            stopSelf()
//        }
//    }
//
//    private fun startCoroutineTimer() {
//        launch(coroutineContext) {
//            handler.post(runnable)
//        }
//    }
//}