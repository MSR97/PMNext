package com.pomonext.pomonext.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.pomonext.pomonext.screens.dashboard.DashboardScreen
import com.pomonext.pomonext.screens.home.HomeScreen
import com.pomonext.pomonext.screens.login.LoginScreen
import com.pomonext.pomonext.screens.pomorun.PomoRunScreen
import com.pomonext.pomonext.screens.splash.SplashScreen
import com.pomonext.pomonext.screens.tasks.TasksScreen

@Composable
fun PomoNavigation() {
    val navController = rememberNavController()
    val isUserLoggedIn = !(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())


    NavHost(
        navController = navController,
//        startDestination = if (isUserLoggedIn) PomoScreens.HomeScreen.name else PomoScreens.LoginScreen.name
        startDestination = if (isUserLoggedIn) PomoScreens.PomoRunScreen.name else PomoScreens.LoginScreen.name

    ) {
        composable(PomoScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(PomoScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(PomoScreens.PomoRunScreen.name) {
            PomoRunScreen(navController = navController)
        }
        composable(PomoScreens.DetailedTasksScreen.name) {
            TasksScreen(navController = navController)
        }
        composable(PomoScreens.LoginScreen.name) {
            LoginScreen(navController = navController)

        }
        composable(PomoScreens.DashboardScreen.name) {
            DashboardScreen(navController = navController)

        }


    }
}