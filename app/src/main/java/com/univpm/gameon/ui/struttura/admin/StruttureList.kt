import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.EditStrutturaRoute
import com.univpm.gameon.core.NuovaStrutturaRoute
import com.univpm.gameon.ui.components.BackgroundScaffold
import com.univpm.gameon.ui.components.ButtonComponent
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.viewmodels.StruttureViewModel
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.core.getCurrentLocation

@Composable
fun StruttureListScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val struttureViewModel: StruttureViewModel = hiltViewModel()

    val strutture by struttureViewModel.strutture.collectAsState()

    var userPosition by remember { mutableStateOf<LatLng?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

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

    BackgroundScaffold {
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
