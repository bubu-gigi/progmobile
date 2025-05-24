package com.univpm.gameon.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.univpm.gameon.ui.AdminHomeScreen
import com.univpm.gameon.ui.CarteListScreen
import com.univpm.gameon.ui.ChatListAdminScreen
import com.univpm.gameon.ui.ChatListScreen
import com.univpm.gameon.ui.ChatScreen
import com.univpm.gameon.ui.EditProfileScreen
import com.univpm.gameon.ui.GiocatoreHomeScreen
import com.univpm.gameon.ui.LoginScreen
import com.univpm.gameon.ui.NuovaCartaScreen
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

@Serializable
object EditProfileScreenRoute

@Serializable
object CarteListScreenRoute

@Serializable
object NuovaCartaScreenRoute

@Serializable
object ChatListScreenRoute

@Serializable
data class  ChatScreenRoute(
    val strutturaId: String,
    val strutturaNome: String,
    val giocatoreId: String?
)

@Serializable
object ChatListAdminScreenRoute

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

        composable<EditProfileScreenRoute> {
            EditProfileScreen(navController)
        }

        composable<CarteListScreenRoute> {
            CarteListScreen(navController)
        }

        composable<NuovaCartaScreenRoute> {
            NuovaCartaScreen(navController)
        }

        composable<ChatListScreenRoute> {
            ChatListScreen(navController)
        }

        composable<ChatScreenRoute> { entry ->
            val args = entry.toRoute<ChatScreenRoute>()
            ChatScreen(navController, args.strutturaId, args.strutturaNome, args.giocatoreId ?: "")
        }

        composable<ChatListAdminScreenRoute> {
            ChatListAdminScreen(navController)
        }
    }
}
