package com.pomonext.pomonext.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pomonext.pomonext.R
import com.pomonext.pomonext.navigation.PomoScreens
import com.pomonext.pomonext.ui.theme.PomoFirstC
import com.pomonext.pomonext.ui.theme.PomoSecC


@Composable
fun PomoAppBar(
    navHostController: NavHostController,
    title: String = "PomoNext",
    userName: String? = "Mohammad",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
//    navController: NavController
) {

    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier,

            ) {
            if (isMainScreen) {
                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(color = PomoFirstC)) {
                        append("Hey,")
                    }
                    withStyle(
                        style = SpanStyle(

                            color = PomoFirstC
                        )
                    ) {
                        append(" $userName!")
                    }

                })

                Spacer(modifier = Modifier.width(150.dp))
                Image(
                    painter = painterResource(R.drawable.edit_circle),
                    contentDescription = "Contact profile picture",
                    modifier = Modifier
                        // Set image size to 40 dp
                        .size(40.dp)

                )

            }


        }
    }, actions = {
        //Icon

        IconButton(onClick = {
            navHostController.navigate(PomoScreens.DashboardScreen.name)

        }) {
            Image(
                painterResource(id = R.drawable.edit_circle),
                contentDescription = "dashboard",
            )

        }

        IconButton(onClick = {
            navHostController.navigate(PomoScreens.DashboardScreen.name)

        }) {
            Image(
                painterResource(id = R.drawable.profile_avatar),
                contentDescription = "dashboard",
            )

        }
    }, backgroundColor = Color.White, elevation = 12.dp)


}






