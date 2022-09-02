package com.pomonext.pomonext.screens.home

import CategoriesBox
import androidx.compose.foundation.layout.*
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pomonext.pomonext.components.AddPomoTaskFAB
import com.pomonext.pomonext.components.PomoPages
import com.pomonext.pomonext.components.PomoPagesIndicator
import com.pomonext.pomonext.model.MCategory
import com.pomonext.pomonext.model.MPomoCard
import com.pomonext.pomonext.model.MPomoTask
import com.pomonext.pomonext.widgets.PomoAppBar
import com.pomonext.pomonext.widgets.PomoBottomNavigationBar
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    Scaffold(
        topBar = { PomoAppBar(navController) },
        floatingActionButton = { AddPomoTaskFAB {} },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = { PomoBottomNavigationBar(navController = navController) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            //Home Content
            HomeContent(navController)


        }

    }


}

@Composable
fun HomeContent(navController: NavHostController) {
    val currentGridPageIndex = remember {
        mutableStateOf("")
    }
    val totalGridPageIndex = remember {
        mutableStateOf("")
    }
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxHeight()) {
        CategoriesBox()
        PomoPagesIndicator(currentGridPageIndex.value, totalGridPageIndex.value)
        PomoCardGroupsShow() { currentPage, totalPage ->
            currentGridPageIndex.value = currentPage
            totalGridPageIndex.value = totalPage

        }


    }

}


@OptIn(ExperimentalSnapperApi::class)
@Preview
@Composable
fun PomoCardGroupsShow(
    onScroll: (String, String) -> Unit = { currentPage, TotalPage -> }
) {
    /////////////////////////
    //todo Providing Data
    val color1 = Color(0xFFDFD2FA)
    val color2 = Color(0xFFFCD6ED)
    val color3 = Color(0xFFFCDDD2)
    val color4 = Color(0xFFDDDDDD)

    val mPomoTask1 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask2 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask3 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask4 = MPomoTask("id", "Doing Task", "12", false, "test")
    val mPomoTask5 = MPomoTask("id", "Doing Task", "12", false, "test")

    val mPomoTaskList =
        listOf<MPomoTask>(mPomoTask1, mPomoTask2, mPomoTask3, mPomoTask4, mPomoTask5)

    val pomoCard1 = MPomoCard(
        "1", "A", createdDate = "123",
        pomoColor = color1, tasksList = mPomoTaskList
    )
    val pomoCard2 = MPomoCard(
        "2", "B", createdDate = "123",
        pomoColor = color2, tasksList = mPomoTaskList
    )
    val pomoCard3 = MPomoCard(
        "3", "C", createdDate = "123",
        pomoColor = color3, tasksList = mPomoTaskList
    )
    val pomoCard4 = MPomoCard(
        "4", "D", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard5 = MPomoCard(
        "5", "E", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard6 = MPomoCard(
        "6", "F", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard7 = MPomoCard(
        "7", "G", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard8 = MPomoCard(
        "8", "H", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard9 = MPomoCard(
        "9", "I", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )
    val pomoCard10 = MPomoCard(
        "10", "J", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )

    val mPomoCardCardList = listOf(
        pomoCard1, pomoCard2, pomoCard3, pomoCard4,
        pomoCard5, pomoCard6, pomoCard7, pomoCard8,
        pomoCard9, pomoCard10,pomoCard1


        )


    val pomoCategory = MCategory("0", "TestCat", "date", "0", mPomoCardCardList)



    PomoPages(onScroll = onScroll, pomoCategory = pomoCategory) { onPress ->

    }


}






