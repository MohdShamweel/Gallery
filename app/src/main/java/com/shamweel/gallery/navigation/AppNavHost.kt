package com.shamweel.gallery.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.shamweel.gallery.feature.gallery.navigation.homeNavigationRoute
import com.shamweel.gallery.feature.gallery.navigation.homeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen()
    }

}