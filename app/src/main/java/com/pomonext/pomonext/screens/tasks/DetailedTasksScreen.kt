package com.pomonext.pomonext.screens.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pomonext.pomonext.components.AddPomoTaskFAB
import com.pomonext.pomonext.components.PomoCard
import com.pomonext.pomonext.model.MPomoCard
import com.pomonext.pomonext.model.MPomoTask
import com.pomonext.pomonext.ui.theme.PomoTaskCardColor
import com.pomonext.pomonext.widgets.PomoAppBar
import com.pomonext.pomonext.widgets.PomoBottomNavigationBar

@Composable
fun TasksScreen(navController: NavHostController) {
    Scaffold(
        topBar = { PomoAppBar(navController) },
        bottomBar = { PomoBottomNavigationBar(navController = navController) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            //DetailedTaskContent
            DetailedTaskContent(navController)


        }

    }

}

@Composable
fun DetailedTaskContent(navController: NavHostController) {
    //todo Providing Data

    val mPomoTask1 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask2 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask3 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask4 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask5 = MPomoTask("id", "Doing Task", "12", false, "test")

    val mPomoTaskList =
        listOf<MPomoTask>(mPomoTask1, mPomoTask2, mPomoTask3, mPomoTask4, mPomoTask5)
    val pomoCard = MPomoCard(
        "id", "Project", createdDate = "123",
        pomoColor = PomoTaskCardColor, tasksList = mPomoTaskList
    )
//////////////////////////////////////////////////////////////////
    PomoCard(mPomoCard = pomoCard, isItDetailedScreen = true)
}
