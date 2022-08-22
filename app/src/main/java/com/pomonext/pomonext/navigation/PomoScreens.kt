package com.pomonext.pomonext.navigation

enum class PomoScreens {
    SplashScreen,
    HomeScreen,
    LoginScreen,
    DetailedTasksScreen,
    PomoRunScreen,
    DashboardScreen;

    companion object {
        fun fromRoute(route: String?): PomoScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            LoginScreen.name -> LoginScreen
            DetailedTasksScreen.name -> DetailedTasksScreen
            PomoRunScreen.name -> PomoRunScreen
            PomoRunScreen.name-> DashboardScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")

        }

    }
}