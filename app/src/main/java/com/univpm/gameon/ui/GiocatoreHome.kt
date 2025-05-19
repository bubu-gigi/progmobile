package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univpm.gameon.core.EditProfileScreenRoute
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.viewmodels.AuthViewModel

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

        Button(
            onClick = { authViewModel.deleteAccount() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Elimina Account", color = MaterialTheme.colorScheme.onError)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GiocatoreHomeScreenPreview() {
    val fakeNav = rememberNavController()
    GiocatoreHomeScreen(fakeNav)
}
