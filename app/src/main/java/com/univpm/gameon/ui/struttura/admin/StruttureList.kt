package com.univpm.gameon.ui.struttura.admin

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.univpm.gameon.R
import com.univpm.gameon.core.EditStrutturaRoute
import com.univpm.gameon.core.NuovaStrutturaRoute
import com.univpm.gameon.core.getCurrentLocation
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.viewmodels.StruttureViewModel

@Composable
fun StruttureListScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val struttureViewModel: StruttureViewModel = hiltViewModel()

    val strutture by struttureViewModel.strutture.collectAsState()

    var userPosition by remember { mutableStateOf<LatLng?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> permissionGranted = granted }
    )

    LaunchedEffect(Unit) {
        struttureViewModel.caricaStrutture()

        val alreadyGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {
            permissionGranted = true
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            userPosition = getCurrentLocation(context)
        }
    }

    BackgroundScaffold(backgroundResId = R.drawable.sfondocarta) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = { struttura ->
                    navController.navigate(EditStrutturaRoute(struttura.id))
                },
                userPosition = userPosition,
                cameraPositionState = cameraPositionState,
                height = 400.dp
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonComponent(
                onClick = {
                    navController.navigate(NuovaStrutturaRoute)
                },
                text = "Inserisci nuova struttura"
            )
        }
    }
}
