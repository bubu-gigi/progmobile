package com.univpm.gameon.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.univpm.gameon.ui.AdminHomeScreen
import com.univpm.gameon.ui.GiocatoreHomeScreen
import com.univpm.gameon.ui.LoginScreen
import com.univpm.gameon.ui.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
object GiocatoreHomeScreenRoute

@Serializable
object AdminHomeScreenRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreenRoute) {

        composable<LoginScreenRoute> {
            LoginScreen(navController)
        }

        composable<RegisterScreenRoute> {
            RegisterScreen(navController)
        }

        composable<GiocatoreHomeScreenRoute> {
            GiocatoreHomeScreen(navController)
        }

        composable<AdminHomeScreenRoute> {
            AdminHomeScreen(navController)
        }
    }
}
