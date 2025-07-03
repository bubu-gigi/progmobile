package com.univpm.gameon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.univpm.gameon.R
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.RoundedButtonComponent

@Composable
fun HomeScreen(
    onAccediClick: () -> Unit,
    onRegistratiClick: () -> Unit
) {
    BackgroundScaffold(backgroundResId = R.drawable.sfondointro) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(470.dp))

            RoundedButtonComponent(
                text = "Accedi",
                onClick = onAccediClick,
                modifier = Modifier
                    .padding(20.dp)
            )

            RoundedButtonComponent(
                text = "Registrati",
                onClick = onRegistratiClick,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
