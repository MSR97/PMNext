package com.pomonext.pomonext.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pomonext.pomonext.R

@Composable
fun TomatoLogo(modifier: Modifier = Modifier) {
    Image(
        painterResource(id = R.drawable.tomato_logo),
        contentDescription = "Logo",
        modifier = modifier
            .padding(5.dp)
            .size(60.dp)
    )
}

@Composable
fun SocialMediaLogos(modifier: Modifier = Modifier) {
    Image(
        painterResource(id = R.drawable.google),
        contentDescription = null,
        modifier = modifier
            .padding(5.dp)
            .size(60.dp)
    )
    Image(
        painterResource(id = R.drawable.facebook),
        contentDescription = null,
        modifier = modifier
            .padding(5.dp)
            .size(60.dp)
    )
}