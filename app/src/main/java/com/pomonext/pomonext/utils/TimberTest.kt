package com.pomonext.pomonext.utils

import timber.log.Timber

fun logC(message: String, tag: String? = null) {
    if (tag == null) {
        Timber.d(message)
    } else {
        Timber.tag(tag).d(message)

    }
}