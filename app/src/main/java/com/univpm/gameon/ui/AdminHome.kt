package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.univpm.gameon.core.checkAccess
import com.univpm.gameon.core.logout
import com.univpm.gameon.data.collections.enums.UserRuolo

@Composable
fun AdminHomeScreen(navController: NavController) {
    checkAccess(navController, UserRuolo.Admin)
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Benvenuto, Admin!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { logout(navController) }) {
            Text("Logout")
        }
    }

}
