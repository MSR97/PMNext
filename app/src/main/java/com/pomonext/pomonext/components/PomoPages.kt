package com.pomonext.pomonext.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomonext.pomonext.model.MCategory
import com.pomonext.pomonext.model.MPomoCard
import com.pomonext.pomonext.utils.logC
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior


const val numOfCols = 2
const val numOfRows = 2

@Preview
@Composable
fun PomoPages(
    pomoCategory: MCategory = MCategory(
        "id", "1", "date", "0",
        pomoCardsList = emptyList()
    ),
    numberOfItemsInPage: Int = 4,
    onScroll: (String, String) -> Unit = { currentPage, TotalPage -> },
    onCardPressed: (String) -> Unit = {}

) {
    PomoCardPages(pomoCategory, numberOfItemsInPage, onScroll)
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private fun PomoCardPages(
    pomoCategory: MCategory,
    numberOfItemsInPage: Int,
    onScroll: (String, String) -> Unit
) {
    val mPomoPages = pomoCategory.pomoCardsList.chunked(numberOfItemsInPage)
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

        items(mPomoPages) { pomoPage ->
            PomoCardSinglePage(pomoPage)
            logC(pomoPage[0].pomoTitle, "POMOPAGE")
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
                mPomoPages.size.toString()
            )
        }
    }
}

@Composable
private fun PomoCardSinglePage(pomoGroup: List<MPomoCard>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var colNum = 0
        for (i in 0 until numOfCols) {
            PomoRow(pomoGroup, colNum)
            colNum += 2
        }
    }
}

@Composable
private fun PomoRow(pomoCard: List<MPomoCard>, colNum: Int) {
    var index = 0
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    ) {
        for (j in 0 until numOfRows) {
            index = j + colNum
            if (index < pomoCard.size) {
                PomoCard(mPomoCard = pomoCard[index])
            }
        }

    }
}