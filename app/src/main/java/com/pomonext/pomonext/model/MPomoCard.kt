package com.pomonext.pomonext.model

import androidx.compose.ui.graphics.Color

data class MPomoCard(
    var id: String,
    var pomoTitle: String,
    var createdDate: String,
    var tasksList: List<MPomoTask>,
    var pomoColor: Color
)
