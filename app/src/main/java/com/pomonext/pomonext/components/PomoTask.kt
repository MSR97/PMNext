package com.pomonext.pomonext.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pomonext.pomonext.model.MPomoTask
import com.pomonext.pomonext.ui.theme.PomoSecC

@Preview
@Composable

fun PomoTask(
    modifier: Modifier = Modifier,
    task: MPomoTask = MPomoTask(
        "test",
        "Doing task",
        "566", true,
        "test"
    ),
    isItDetailedScreen: Boolean = false
) {
    val isChecked = remember { mutableStateOf(task.isItDone) }
    val title = remember { mutableStateOf(task.taskTitle) }
    val circleSize = remember { mutableStateOf(20.dp) }
    val color = remember { mutableStateOf(Color.Gray) }
    Row(
        horizontalArrangement = if (!isItDetailedScreen) Arrangement.Center else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                top = 15.dp, start = if (!isItDetailedScreen) 0.dp else 15.dp,
                bottom = if (!isItDetailedScreen) 0.dp else 10.dp
            )
            .toggleable(value = isChecked.value, role = Role.Checkbox) {
                isChecked.value = it
                task.isItDone = it

            }) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .size(circleSize.value)
                .background(color.value)
                .padding(0.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked.value) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "", tint = PomoSecC)
            }
        }

        Text(
            text = title.value,
            color = color.value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}