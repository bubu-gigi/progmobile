import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.EditStrutturaRoute
import com.univpm.gameon.core.NuovaStrutturaRoute
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

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        MappaStruttureConFiltri(
            strutture = strutture,
            onStrutturaSelezionata = { struttura ->
                navController.navigate(EditStrutturaRoute(struttura.id))
            },
            height = 400.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(NuovaStrutturaRoute)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            )
        ) {
            Text(text = "Inserisci nuova struttura")
        }
    }
}
