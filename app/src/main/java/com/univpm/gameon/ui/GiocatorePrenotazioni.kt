
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univpm.gameon.core.StrutturaDettaglioRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.ui.futuraBookFontFamily
import com.univpm.gameon.ui.lemonMilkFontFamily
import com.univpm.gameon.viewmodels.PrenotazioneViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel

@Composable
fun GiocatorePrenotazioniScreen(navController: NavController) {
    val struttureViewModel: StruttureViewModel = hiltViewModel()
    val prenotazioniViewModel: PrenotazioneViewModel = hiltViewModel()

    val strutture by struttureViewModel.strutture.collectAsState()
    val prenotazioni by prenotazioniViewModel.prenotazioniUtente.collectAsState()

    LaunchedEffect(Unit) {
        struttureViewModel.caricaStrutture()
        prenotazioniViewModel.caricaPrenotazioniUtente(UserSessionManager.userId.orEmpty())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Le tue prenotazioni", color = Color.White, fontSize = 20.sp, fontFamily = lemonMilkFontFamily)
            if (prenotazioni.isEmpty()) {
                Text("Nessuna prenotazione trovata", color = Color.White)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(prenotazioni) { prenotazione ->
                        val struttura = strutture.find { it.id == prenotazione.strutturaId }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        StrutturaDettaglioRoute(
                                            struttura?.id ?: ""
                                        )
                                    )
                                }
                                .border(BorderStroke(2.dp, Color(0xFFE36BE0))),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = struttura?.nome ?: "Struttura sconosciuta",
                                    color = Color.White,
                                    fontFamily = lemonMilkFontFamily,
                                    fontSize = 18.sp
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Campo: ${prenotazione.campoId}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                                Text(
                                    text = "Data: ${prenotazione.data}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                                Text(
                                    text = "Orario: ${prenotazione.orari}",
                                    color = Color.White,
                                    fontFamily = futuraBookFontFamily
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = {struttura -> navController.navigate(StrutturaDettaglioRoute(struttura.id))},
                height = 300.dp
            )
        }
    }
}
