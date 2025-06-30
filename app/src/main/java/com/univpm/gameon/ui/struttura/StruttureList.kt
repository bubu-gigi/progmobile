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

@Composable
fun StruttureListScreen(
    navController: NavController
) {
    val struttureViewModel: StruttureViewModel = hiltViewModel()

    val strutture by struttureViewModel.strutture.collectAsState()

    LaunchedEffect(Unit) {
        struttureViewModel.caricaStrutture()
    }

    BackgroundScaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = { struttura ->
                    navController.navigate(EditStrutturaRoute(struttura.id))
                },
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
