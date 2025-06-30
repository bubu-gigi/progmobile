package com.univpm.gameon.core

import GiocatorePrenotazioniScreen
import StruttureListScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.univpm.gameon.ui.AdminHomeScreen
import com.univpm.gameon.ui.CarteListScreen
import com.univpm.gameon.ui.ChatListAdminScreen
import com.univpm.gameon.ui.ChatListScreen
import com.univpm.gameon.ui.ChatScreen
import com.univpm.gameon.ui.auth.EditProfileScreen
import com.univpm.gameon.ui.GiocatoreHomeScreen
import com.univpm.gameon.ui.NuovaCartaScreen
import com.univpm.gameon.ui.auth.LoginScreen
import com.univpm.gameon.ui.auth.RegisterScreen
import com.univpm.gameon.ui.struttura.StrutturaDettaglioScreen
import com.univpm.gameon.ui.struttura.StrutturaFormScreen
import com.univpm.gameon.viewmodels.StruttureViewModel
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

@Serializable
object StruttureListRoute

@Serializable
object NuovaStrutturaRoute

@Serializable
object GiocatorePrenotazioniRoute

@Serializable
data class EditStrutturaRoute(
    val strutturaId: String
)

@Serializable
data class StrutturaDettaglioRoute(
    val strutturaId: String
)

@RequiresApi(Build.VERSION_CODES.O)
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
            val viewModel: StruttureViewModel = hiltViewModel()

            val struttura by viewModel.strutturaSelezionata.collectAsState()
            val campi by viewModel.campiStruttura.collectAsState()

            LaunchedEffect(args.strutturaId) {
                viewModel.caricaStruttura(args.strutturaId)
            }

            if (struttura != null) {
                StrutturaFormScreen(
                    navController = navController,
                    strutturaDaModificare = struttura,
                    campiEsistenti = campi
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Caricamento struttura...")
                }
            }
        }
        composable<StrutturaDettaglioRoute> { entry ->
            val args = entry.toRoute<StrutturaDettaglioRoute>()
            val viewModel: StruttureViewModel = hiltViewModel()

            val struttura by viewModel.strutturaSelezionata.collectAsState()
            val campi by viewModel.campiStruttura.collectAsState()
            val errore by viewModel.errore.collectAsState()

            LaunchedEffect(args.strutturaId) {
                viewModel.caricaStruttura(args.strutturaId)
            }

            when {
                errore != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Errore: $errore")
                    }
                }

                struttura != null -> {
                    StrutturaDettaglioScreen(
                        navController = navController,
                        struttura = struttura!!,
                        campi = campi
                    )
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Caricamento struttura...")
                    }
                }
            }
        }
    }
}
