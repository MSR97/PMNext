package com.pomonext.pomonext.service.impl

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.pomonext.pomonext.model.PomoRunState
import com.pomonext.pomonext.model.PomoRunType
import com.pomonext.pomonext.screens.pomorun.PomoRunViewModel
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_PAUSED
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_IS_RUNNING
import com.pomonext.pomonext.utils.Constants.NOTIFICATION_TIMER_TEXT
import com.pomonext.pomonext.utils.Constants.POMORUN_INTENT
import com.pomonext.pomonext.utils.Constants.POMORUN_PAUSE_ACTION
import javax.inject.Inject

class PomoPunBroadCastReceiver @Inject constructor(val viewModel: PomoRunViewModel) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == POMORUN_INTENT) {
            viewModel.timerCurrentValue.value =
                (intent.getIntExtra(NOTIFICATION_TIMER_TEXT, 0))
            viewModel.isPomoRunning.value =
                (intent.getBooleanExtra(NOTIFICATION_TIMER_IS_RUNNING, false))
            viewModel.isPomoPaused.value =
                (intent.getBooleanExtra(NOTIFICATION_TIMER_IS_PAUSED, false))
        }
    }


//    private fun setUpPomoRun(): Int {
//        return if (serviceState == PomoRunState.Finished) {
//            if (pomoRunType == PomoRunType.Focus) {
//                Toast.makeText(applicationContext, "Focus Finished", Toast.LENGTH_SHORT).show()
//                pomoRunType = PomoRunType.Break
//                return _breakTime
//
//
//            } else {
//                Toast.makeText(applicationContext, "Break Finished", Toast.LENGTH_SHORT).show()
//                pomoRunType = PomoRunType.Focus
//                return _focusTime
//            }
//        } else {
//            _focusTime
//        }
//    }


}



