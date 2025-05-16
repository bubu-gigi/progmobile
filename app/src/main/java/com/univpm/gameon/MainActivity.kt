package com.univpm.gameon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.ui.BookingDetailScreen
import com.univpm.gameon.viewmodels.UserViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = ScreenA
            ) {
                composable <ScreenA> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick =  {navController.navigate(ScreenB(
                            name = "Nich",
                            age = 26,
                        ))
                        }) {
                            Text(text = "Go to ScreenB")
                        }
                    }
                }
                composable<ScreenB> {
                    val args = it.toRoute<ScreenB>()
                    val userViewModel: UserViewModel = viewModel()

                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "${args.name}, ${args.age} years old")

                        Button(onClick = {
                            navController.navigate(ScreenC(items = listOf("Uno", "Due", "Tre")))
                        }) {
                            Text("Vai a ScreenC")
                        }

                        Button(onClick = {
                            userViewModel.createUser()
                        }) {
                            Text("Crea utente in Firestore")
                        }

                        Button(onClick = {
                            userViewModel.loadUser("u123") // puoi usare id dinamico se vuoi
                        }) {
                            Text("Carica utente da Firestore")
                        }

                        Button(onClick = {
                            navController.navigate(ScreenA)
                        }) {
                            Text(text = "Home Button")
                        }
                    }
                }
                composable<ScreenC> {
                    val args = it.toRoute<ScreenC>()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Lista ricevuta:")
                        args.items.forEachIndexed { index, item ->
                            Text(text = "${index + 1}. $item")
                        }
                        Button(onClick = { navController.navigate(ScreenA) }) {
                            Text(text = "Home Button")
                        }
                    }
                }

            }
        }
    }
}



@Serializable
object ScreenA

@Serializable
data class ScreenB (
    val name: String?,
    val age: Int?,
)

@Serializable
data class ScreenC (
    val items: List<String>
)


@Preview (showBackground = true, name = "Detail Screen Preview")
@Composable
fun PreviewBookingDetailScreen() {
    BookingDetailScreen(bookingId = "booking_123")
}
