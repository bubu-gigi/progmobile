package com.univpm.gameon.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.CarteListScreenRoute
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.viewmodels.AuthViewModel
import com.univpm.gameon.R

@Composable
fun GiocatoreHomeScreen(navController: NavController) {
    checkAccess(navController)

    val authViewModel: AuthViewModel = hiltViewModel()

    LaunchedEffect(authViewModel.destination.value) {
        authViewModel.destination.value?.let {
            navController.navigate(it)
            authViewModel.destination.value = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Benvenuto, Giocatore!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { logout(navController) }) {
            Text("Logout")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(EditProfileScreenRoute) }) {
            Text("Modifica Profilo")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate(CarteListScreenRoute) }) {
            Text("Gestione Carte")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { authViewModel.deleteAccount() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Elimina Account", color = MaterialTheme.colorScheme.onError)
        }
    }
}

@Composable
fun GiocatoreHomeContent(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    onGestioneCarte: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    // Sostituisci "background_image" con il nome esatto dell'immagine nella cartella drawable
    val background = painterResource(id = R.drawable.sfondobase)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Sfondo immagine
        Image(
            painter = background,
            contentDescription = null,
            contentScale = ContentScale.Crop, // Adatta l'immagine allo schermo
            modifier = Modifier.matchParentSize()
        )

        // Contenuto sovrapposto allo sfondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(390.dp))
            Text("Benvenuto, Giocatore!", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onLogout) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onEditProfile) {
                Text("Modifica Profilo")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onGestioneCarte) {
                Text("Gestione Carte")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onDeleteAccount,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Elimina Account",
                    color = MaterialTheme.colorScheme.onError,
                    fontFamily = futuraBookFontFamily
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GiocatoreHomeContentPreview() {
    GiocatoreHomeContent(
        onLogout = {},
        onEditProfile = {},
        onGestioneCarte = {},
        onDeleteAccount = {}
    )
}
