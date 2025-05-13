package com.univpm.gameon.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.univpm.gameon.ui.BookingDetailScreen
import com.univpm.gameon.ui.BookingHomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            BookingHomeScreen { bookingId ->
                navController.navigate("detail/$bookingId")
            }
        }
        composable("detail/{bookingId}") { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId") ?: ""
            BookingDetailScreen(bookingId)
        }
    }
}
