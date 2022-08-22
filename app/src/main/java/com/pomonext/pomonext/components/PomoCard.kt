package com.pomonext.pomonext.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pomonext.pomonext.R
import com.pomonext.pomonext.model.MPomoCard
import com.pomonext.pomonext.model.MPomoTask
import com.pomonext.pomonext.ui.theme.PomoSecC
import com.pomonext.pomonext.utils.getScreenWidth

@Preview(showBackground = false)
@Composable
fun PomoCard(
    modifier: Modifier = Modifier,
    mPomoCard: MPomoCard
    = MPomoCard("id", "Project", "2323", emptyList(), Color(0xFFDACAFC)),
    isItDetailedScreen: Boolean = false,
    numberOfCardInPage: Int = 2,
    onPressDetails: (String) -> Unit = {}
) {
    var backGroundColor = mPomoCard.pomoColor
    val pomoCardWidth = getScreenWidth(context = LocalContext.current) / numberOfCardInPage
    Card(
        modifier = modifier
            .width(pomoCardWidth.dp)
            .height(230.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = backGroundColor,
        elevation = 5.dp
    ) {
        if (isItDetailedScreen) {
            Column(
                modifier = Modifier.padding(top = 40.dp, end = 2.dp),
                horizontalAlignment = Alignment.End
            ) {
                AddButton()
                DeleteButton()

            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = mPomoCard.pomoTitle,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PomoSecC)
            )


            val tasksList = listOf<MPomoTask>(
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task"),
                MPomoTask("test", "Doing task")
            )

            LazyColumn(
                horizontalAlignment = if (isItDetailedScreen) Alignment.CenterHorizontally else Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = if (!isItDetailedScreen) Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 5.dp)
                    .height(190.dp)
                    .fillMaxHeight()
                else {
                    Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 15.dp, top = 0.dp)
                        .fillMaxHeight()
                }


            ) {
                items(tasksList) { pomoTask ->
                    PomoTask(task = pomoTask, isItDetailedScreen = isItDetailedScreen)


                }

            }

        }

    }

}

@Composable
fun AddButton() {
    IconButton(
        modifier = Modifier.size(58.dp),
        onClick = { /*TODO*/ },

        ) {
        Image(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add Task"
        )

    }

}

@Composable
fun DeleteButton() {
    IconButton(
        modifier = Modifier
            .size(58.dp),
        onClick = { Log.d("TestButton", "Pressed") },

        ) {
        Image(painter = painterResource(id = R.drawable.delete), contentDescription = "Delete Task")

    }

}


