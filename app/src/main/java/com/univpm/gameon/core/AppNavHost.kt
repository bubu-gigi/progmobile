package com.univpm.gameon.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.univpm.gameon.ui.AdminHomeScreen
import com.univpm.gameon.ui.carte.CarteListScreen
import com.univpm.gameon.ui.chat.ChatListAdminScreen
import com.univpm.gameon.ui.chat.ChatListScreen
import com.univpm.gameon.ui.chat.ChatScreen
import com.univpm.gameon.ui.prenotazioni.GestionePrenotazioniAdminScreen
import com.univpm.gameon.ui.auth.EditProfileScreen
import com.univpm.gameon.ui.GiocatoreHomeScreen
import com.univpm.gameon.ui.carte.NuovaCartaScreen
import com.univpm.gameon.ui.auth.LoginScreen
import com.univpm.gameon.ui.auth.RegisterScreen
import com.univpm.gameon.ui.prenotazioni.GiocatorePrenotazioniScreen
import com.univpm.gameon.ui.struttura.admin.StrutturaFormScreen
import com.univpm.gameon.ui.struttura.admin.StruttureListScreen
import com.univpm.gameon.ui.struttura.giocatore.StrutturaDettaglioScreen
import com.univpm.gameon.ui.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenRoute

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

@Serializable
object StruttureListRoute

@Serializable
object NuovaStrutturaRoute

@Serializable
object GiocatorePrenotazioniRoute

@Serializable
object GestionePrenotazioniAdminRoute

@Serializable
data class EditStrutturaRoute(
    val strutturaId: String
)

@Serializable
data class StrutturaDettaglioRoute(
    val strutturaId: String,
    val prenotazioneId: String?
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HomeScreenRoute) {

        composable<HomeScreenRoute> {
            HomeScreen(
                onAccediClick = { navController.navigate(LoginScreenRoute) },
                onRegistratiClick = { navController.navigate(RegisterScreenRoute) }
            )
        }

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
            ChatScreen(args.strutturaId, args.strutturaNome, args.giocatoreId ?: "")
        }

        composable<ChatListAdminScreenRoute> {
            ChatListAdminScreen(navController)
        }

        composable<StruttureListRoute> {
            StruttureListScreen(navController)
        }

        composable<NuovaStrutturaRoute> {
            StrutturaFormScreen(navController)
        }

        composable<GiocatorePrenotazioniRoute> {
            GiocatorePrenotazioniScreen(navController)
        }

        composable<EditStrutturaRoute> { entry ->
            val args = entry.toRoute<EditStrutturaRoute>()
            StrutturaFormScreen(navController = navController, strutturaId = args.strutturaId)
        }

        composable<StrutturaDettaglioRoute> { entry ->
            val args = entry.toRoute<StrutturaDettaglioRoute>()
            StrutturaDettaglioScreen(navController = navController, strutturaId = args.strutturaId, prenotazioneId = args.prenotazioneId)
        }

        composable<GestionePrenotazioniAdminRoute> {
            GestionePrenotazioniAdminScreen()
        }
    }
}
