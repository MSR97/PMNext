package com.pomonext.pomonext.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pomonext.pomonext.R
import com.pomonext.pomonext.navigation.PomoNavBarItems


@Composable
fun PomoBottomNavigationBar(navController: NavHostController) {
    Divider(thickness = 5.dp, modifier = Modifier.shadow(5.dp, shape = RectangleShape, clip = true))

    BottomNavigation(
        modifier = Modifier
            .height(70.dp)
            .padding(1.dp)
            .shadow(20.dp, shape = RectangleShape, clip = true),
        backgroundColor = Color.White,
        elevation = 50.dp
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        PomoNavBarItems.pomoNavigationBarItems.forEach { navItem ->
            BottomNavigationItem(
                modifier = Modifier.align(CenterVertically),
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {


                        if (navItem.title == "PomoRun") {
                            Image(
                                painter = painterResource(id = navItem.imageRef),
                                contentDescription = navItem.title,
                                modifier = Modifier
                                    .size(200.dp)
                                    .scale(1.2f)
                                    .padding(bottom = 4.dp)

//                                    .background(Color.Transparent),


                            )
                        } else {
                            Image(
                                painter = painterResource(id = navItem.imageRef),
                                contentDescription = navItem.title,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(top = 0.dp)
                            )
                        }
                        if (currentRoute == navItem.route) {
                            Image(
                                painter = painterResource(id = R.drawable.itemindicator),
                                contentDescription = navItem.title,
                                modifier = Modifier.size(7.dp)
                            )
                        }

                    }
                },
//                label = {
//                    Text(text = navItem.title)
//                },
            )

        }
    }

}
