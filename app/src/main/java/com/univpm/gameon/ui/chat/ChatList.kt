package com.univpm.gameon.ui.chat

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.univpm.gameon.R
import com.univpm.gameon.core.ChatScreenRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.core.getCurrentLocation
import com.univpm.gameon.core.lemonMilkFontFamily
import com.univpm.gameon.ui.components.ConversazioneCard
import com.univpm.gameon.ui.components.MappaStruttureConFiltri
import com.univpm.gameon.viewmodels.ChatViewModel
import com.univpm.gameon.viewmodels.StruttureViewModel

@Composable
fun ChatListScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: ChatViewModel = hiltViewModel()
    val struttureViewModel: StruttureViewModel = hiltViewModel()

    val conversazioni by viewModel.conversazioni.collectAsState()
    val strutture by struttureViewModel.strutture.collectAsState()

    var userPosition by remember { mutableStateOf<LatLng?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            permissionGranted = granted
        }
    )

    LaunchedEffect(Unit) {
        val alreadyGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {
            permissionGranted = true
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val userId = UserSessionManager.userId
        if (!userId.isNullOrBlank()) {
            viewModel.loadConversazioniForUser(userId)
        }
        struttureViewModel.caricaStrutture()
    }

    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            userPosition = getCurrentLocation(context)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sfondocarta),
            contentDescription = "Sfondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Spacer(modifier = Modifier.height(210.dp))

            Text(
                text = "Le tue conversazioni:",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontSize = 23.sp,
                    fontFamily = lemonMilkFontFamily
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(conversazioni) { conv ->
                    val strutturaNome = strutture.find { it.id == conv.strutturaId }?.nome ?: conv.strutturaId

                    ConversazioneCard(
                        strutturaNome = strutturaNome,
                        ultimoMessaggio = conv.ultimoMessaggio,
                        onClick = {
                            navController.navigate(
                                ChatScreenRoute(conv.strutturaId, strutturaNome, conv.giocatoreId)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            MappaStruttureConFiltri(
                strutture = strutture,
                onStrutturaSelezionata = { struttura ->
                    navController.navigate(
                        ChatScreenRoute(
                            strutturaId = struttura.id,
                            strutturaNome = struttura.nome,
                            giocatoreId = null
                        )
                    )
                },
                userPosition = userPosition,
                height = 300.dp,
                width = Dp.Unspecified
            )
        }
    }
}

