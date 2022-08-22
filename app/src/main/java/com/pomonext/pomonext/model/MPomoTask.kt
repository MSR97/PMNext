package com.pomonext.pomonext.model

data class MPomoTask(
    var id: String,
    var taskTitle: String,
    var createdDate: String="",
    var isItDone: Boolean = false,
    var toPomo:String=""
)
