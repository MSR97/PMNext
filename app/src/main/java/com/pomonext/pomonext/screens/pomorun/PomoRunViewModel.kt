package com.pomonext.pomonext.screens.pomorun

import android.app.Application
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.pomonext.pomonext.temp.service.PomoRunForeGroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PomoRunViewModel @Inject constructor(
    private val pomoRunService:PomoRunForeGroundService
) : ViewModel(){
    
//    val appContext = LocalContext.current.applicationContext
//    val serviceIntent = Intent(LocalContext.current, PomoRunForeGroundService::class.java)
//    serviceIntent.putExtra("inputExtra", "A message toForeground Service")
    //    appContext.startService(serviceIntent)

}