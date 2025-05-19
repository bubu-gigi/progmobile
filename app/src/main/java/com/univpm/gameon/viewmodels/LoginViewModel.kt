package com.univpm.gameon.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.univpm.gameon.core.AdminHomeScreenRoute
import com.univpm.gameon.core.GiocatoreHomeScreenRoute
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.enums.UserRuolo
import com.univpm.gameon.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel(){

    private val auth = FirebaseAuth.getInstance()

    var loginState: MutableState<String?> = mutableStateOf(null)
    val destination: MutableState<Any?> = mutableStateOf(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                val uid = auth.currentUser?.uid ?: throw Exception("UID non disponibile")

                val user = userRepository.getUserById(uid)

                UserSessionManager.isLoggedIn = true
                UserSessionManager.userRole = user?.ruolo

                destination.value = when (user?.ruolo) {
                    UserRuolo.Admin -> AdminHomeScreenRoute
                    UserRuolo.Giocatore -> GiocatoreHomeScreenRoute
                    else -> LoginScreenRoute
                }
                loginState.value = "SUCCESS"
            } catch (e: Exception) {
                loginState.value = "FAILED: ${e.message}"
            }
        }
    }
}

