package com.pomonext.pomonext.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomonext.pomonext.model.MPomoGroups

@Preview
@Composable
fun PomoGroup(
    pomoGroup: MPomoGroups = MPomoGroups("id", "1", emptyList()),
    onCardPressed: (String) -> Unit = {}

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {
            PomoCard(mPomoCard = pomoGroup.pomoCardsList[0])
            PomoCard(mPomoCard = pomoGroup.pomoCardsList[1])

        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {
            PomoCard(mPomoCard = pomoGroup.pomoCardsList[2])
            PomoCard(mPomoCard = pomoGroup.pomoCardsList[3])

        }

    }


}