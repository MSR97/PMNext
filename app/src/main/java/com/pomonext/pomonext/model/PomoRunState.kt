package com.pomonext.pomonext.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PomoRunState : Parcelable {
    INITIALIZED,
    START,
    PAUSE,
    STOP,
    Finished
}