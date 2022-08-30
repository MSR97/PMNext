package com.pomonext.pomonext.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.pomonext.pomonext.R
import com.pomonext.pomonext.app.MainActivity
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.service.impl.PomoRunForeGroundServiceImpl
import com.pomonext.pomonext.utils.Constants
import com.pomonext.pomonext.utils.Constants.PomoRunNotificationCHANNEL_ID


class PomoRunNotificationHelper(private val context: Context) {
    var intent: Intent? = null

    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, PomoRunNotificationCHANNEL_ID)
            .setContentTitle(context.getString(R.string.pomoRun_notification_Title))
            .setSound(null)
            .setOngoing(true)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(false)
            .setStyle(NotificationCompat.BigTextStyle())
            .addAction(
                R.drawable.pausebutton, context.getString(R.string.pomoRun_notification_pause),
                onPauseAction(context)
            )
    }

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val contentIntent by lazy {
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun getNotification(): Notification {
        notificationManager.createNotificationChannel(createChannel())

        return notificationBuilder.build()
    }

    fun updateNotification(notificationText: String? = null) {
        notificationText?.let { notificationBuilder.setContentText(it) }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createChannel(): NotificationChannel {
        val channelName = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(PomoRunNotificationCHANNEL_ID, channelName, importance).apply {
                description = descriptionText
                setSound(null, null)
            }
        return channel
    }

    private fun onPauseAction(context: Context): PendingIntent {

        val pomoRunForeGroundService = Intent(context, PomoRunForeGroundServiceImpl::class.java)
        pomoRunForeGroundService.putExtra(
            (Constants.SERVICE_COMMAND),
            PomoRunState.STOP as Parcelable
        )

        return PendingIntent.getForegroundService(
            context,
            0,
            pomoRunForeGroundService,
            PendingIntent.FLAG_IMMUTABLE
        )


    }


    companion object {
        const val NOTIFICATION_ID = 99
    }
}