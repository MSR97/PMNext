package com.pomonext.pomonext.screens.home

import CategoriesBox
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pomonext.pomonext.components.AddPomoTaskFAB
import com.pomonext.pomonext.components.PomoGroup
import com.pomonext.pomonext.components.PomoPagesIndicator
import com.pomonext.pomonext.model.MPomoCard
import com.pomonext.pomonext.model.MPomoGroups
import com.pomonext.pomonext.model.MPomoTask
import com.pomonext.pomonext.widgets.PomoAppBar
import com.pomonext.pomonext.widgets.PomoBottomNavigationBar
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@Composable
fun HomeScreen(navController: NavHostController) {

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
    pomoGroupList: List<MPomoGroups> = emptyList(),
    onScroll: (String, String) -> Unit = { currentPage, TotalPage -> }
) {


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
        "id", "Project", createdDate = "123",
        pomoColor = color1, tasksList = mPomoTaskList
    )
    val pomoCard2 = MPomoCard(
        "id", "Project", createdDate = "123",
        pomoColor = color2, tasksList = mPomoTaskList
    )
    val pomoCard3 = MPomoCard(
        "id", "Project", createdDate = "123",
        pomoColor = color3, tasksList = mPomoTaskList
    )
    val pomoCard4 = MPomoCard(
        "id", "Project", createdDate = "123",
        pomoColor = color4, tasksList = mPomoTaskList
    )

    val mPomoCardCardList = listOf<MPomoCard>(
        pomoCard1, pomoCard2, pomoCard3, pomoCard4
    )

    val pomoGroup1 = MPomoGroups("id", "1", mPomoCardCardList)
    val pomoGroup2 = MPomoGroups("id", "1", mPomoCardCardList)
    val pomoGroup3 = MPomoGroups("id", "1", mPomoCardCardList)
    val pomoGroup4 = MPomoGroups("id", "1", mPomoCardCardList)

    val mPomoGroupList = listOf<MPomoGroups>(
        pomoGroup1, pomoGroup2, pomoGroup3, pomoGroup4
    )


    //////////////////////////////////////////////////////////////////////////////////////////
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        flingBehavior = rememberSnapperFlingBehavior(
            lazyListState = listState,
//            springAnimationSpec = FloatTweenSpec(2000, 0, LinearOutSlowInEasing),


        ),
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 0.dp, end = 0.dp)


    ) {
        items(mPomoGroupList) { pomoGroup ->
            PomoGroup(pomoGroup = pomoGroup)

        }

    }


    val currentPomoPageIndex = remember {
        derivedStateOf {
            (listState.firstVisibleItemIndex) + 1
        }
    }



    LaunchedEffect(key1 = listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            onScroll(
                currentPomoPageIndex.value.toString(),
                mPomoGroupList.size.toString()
            )
        }
    }
}






