package com.shamweel.gallery.feature.gallery.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shamweel.gallery.feature.gallery.HomeRoute

const val homeNavigationRoute = "homeNavigationRoute"

fun NavController.navigateToGHome(navOptions: NavOptions? = null) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
) {
    composable(
        route = homeNavigationRoute
    ) {
        HomeRoute()
    }
}