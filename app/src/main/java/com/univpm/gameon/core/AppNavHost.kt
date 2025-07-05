package com.univpm.gameon.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

// Le tue route serializable rimangono uguali
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
data class ChatScreenRoute(
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Schermate che non devono avere la TopAppBar (solo la home iniziale)
    val screensWithoutTopBar = setOf(
        "com.univpm.gameon.core.HomeScreenRoute"
    )

    // Schermate principali che non devono avere il pulsante back
    val homeScreens = setOf(
        "com.univpm.gameon.core.HomeScreenRoute",
        "com.univpm.gameon.core.GiocatoreHomeScreenRoute",
        "com.univpm.gameon.core.AdminHomeScreenRoute"
    )

    if (currentRoute in screensWithoutTopBar) {
        // Senza TopAppBar solo per la home iniziale
        NavHost(navController = navController, startDestination = HomeScreenRoute) {
            addNavGraphDestinations(navController)
        }
    } else {
        // Con TopAppBar per tutte le altre schermate
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = getScreenTitle(currentRoute),
                            fontWeight = FontWeight.Bold,
                            fontFamily = lemonMilkFontFamily,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        if (currentRoute !in homeScreens) {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Torna indietro",
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF232323)
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeScreenRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                addNavGraphDestinations(navController)
            }
        }
    }
}

// Funzione per ottenere il titolo della schermata
fun getScreenTitle(route: String?): String {
    return when (route) {
        "com.univpm.gameon.core.HomeScreenRoute" -> ""
        "com.univpm.gameon.core.LoginScreenRoute" -> "GameON"
        "com.univpm.gameon.core.RegisterScreenRoute" -> "GameON"
        "com.univpm.gameon.core.GiocatoreHomeScreenRoute" -> ""
        "com.univpm.gameon.core.AdminHomeScreenRoute" -> ""
        "com.univpm.gameon.core.EditProfileScreenRoute" -> "GameON"
        "com.univpm.gameon.core.CarteListScreenRoute" -> "GameON"
        "com.univpm.gameon.core.NuovaCartaScreenRoute" -> "Le tue carte"
        "com.univpm.gameon.core.ChatListScreenRoute" -> "GameON"
        "com.univpm.gameon.core.ChatListAdminScreenRoute" -> "GameON"
        "com.univpm.gameon.core.StruttureListRoute" -> "GameON"
        "com.univpm.gameon.core.NuovaStrutturaRoute" -> "Le tue Strutture"
        "com.univpm.gameon.core.GiocatorePrenotazioniRoute" -> "GameON"
        "com.univpm.gameon.core.GestionePrenotazioniAdminRoute" -> "GameON"
        else -> when {
            route?.contains("ChatScreenRoute") == true -> "GameON"
            route?.contains("EditStrutturaRoute") == true -> "Le mie strutture"
            route?.contains("StrutturaDettaglioRoute") == true -> "GameON"
            else -> "GameON"
        }
    }
}

// Funzione helper per definire tutte le destinazioni
@RequiresApi(Build.VERSION_CODES.O)
fun androidx.navigation.NavGraphBuilder.addNavGraphDestinations(navController: NavController) {
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