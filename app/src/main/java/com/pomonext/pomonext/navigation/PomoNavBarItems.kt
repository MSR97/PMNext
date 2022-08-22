package com.pomonext.pomonext.navigation

import com.pomonext.pomonext.R
import com.pomonext.pomonext.model.MNavBarItem

object PomoNavBarItems {
    val pomoNavigationBarItems = listOf(
        MNavBarItem(
            title = "Home",
            imageRef = R.drawable.home,
            route = PomoScreens.HomeScreen.name
        ),
        MNavBarItem(
            title = "Statistics",
            imageRef = R.drawable.stats,
            route = PomoScreens.DetailedTasksScreen.name
        ),
        MNavBarItem(
            title = "PomoRun",
            imageRef = R.drawable.ic_pomorun,
            route = PomoScreens.PomoRunScreen.name
        ),
        MNavBarItem(
            title = "Explore",
            imageRef = R.drawable.explore,
            route = ""
        ),
        MNavBarItem(
            title = "Dashboard",
            imageRef = R.drawable.dashboard,
            route = PomoScreens.DashboardScreen.name
        )
    )
}