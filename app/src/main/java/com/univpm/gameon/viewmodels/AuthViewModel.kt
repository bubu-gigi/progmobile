package com.univpm.gameon.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.univpm.gameon.core.AdminHomeScreenRoute
import com.univpm.gameon.core.GiocatoreHomeScreenRoute
import com.univpm.gameon.core.LoginScreenRoute
import com.univpm.gameon.core.UserSessionManager
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    var authState: MutableState<String?> = mutableStateOf(null)
    val destination: MutableState<Any?> = mutableStateOf(null)

    val currentUser: MutableState<User?> = mutableStateOf(null)

    fun loadCurrentUser() {
        viewModelScope.launch {
            val email = auth.currentUser?.email ?: return@launch
            currentUser.value = userRepository.getUserByEmail(email)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                Log.d("RegisterScreen", "Email: '$email' Password: '$password'")
                auth.signInWithEmailAndPassword(email, password).await()
                val user = userRepository.getUserByEmail(email)
                UserSessionManager.isLoggedIn = true
                UserSessionManager.userRole = user?.ruolo
                UserSessionManager.userId = user?.id
                Log.d("AuthViewModel", "Login riuscito per ${user?.email}")
                Log.d("AuthViewModel", "Id ${user?.id}")
                Log.d("AuthViewModel", "Ruolo utente: ${user?.ruolo}")
                Log.d("AuthViewModel", "Destinazione: ${destination.value}")
                destination.value = when (user?.ruolo) {
                    "Admin" -> AdminHomeScreenRoute
                    "Giocatore" -> GiocatoreHomeScreenRoute
                    else -> {
                        LoginScreenRoute
                        print("AIUTO")
                    }
                }
                println(destination.value)
                authState.value = "SUCCESS"
            } catch (e: Exception) {
                authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
                userRepository.saveUser(user.copy())
                authState.value = "SUCCESS"
            } catch (e: Exception) {
                authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun logout() {
        auth.signOut()
        UserSessionManager.clear()
        authState.value = null
        UserSessionManager.isLoggedIn = false
        UserSessionManager.userRole = null
        destination.value = LoginScreenRoute
    }

    fun updateProfile(id: String, updatedUser: User) {
        viewModelScope.launch {
            try {
                val firebaseUser = auth.currentUser
                if (firebaseUser == null) {
                    authState.value = "FAILED: Nessun utente autenticato"
                    return@launch
                }

                if (firebaseUser.email != updatedUser.email) {
                    firebaseUser.updateEmail(updatedUser.email).await()
                }

                firebaseUser.updatePassword(updatedUser.password).await()

                userRepository.updateUser(id, updatedUser)

                UserSessionManager.userRole = updatedUser.ruolo
                UserSessionManager.userId = updatedUser.id

                authState.value = "UPDATED"
            } catch (e: Exception) {
                authState.value = "FAILED: ${e.message}"
            }
        }
    }


    fun deleteAccount() {
        viewModelScope.launch {
            try {
                Log.d("AuthViewModel", "Current user ${UserSessionManager.userId}")
                userRepository.removeUser(UserSessionManager.userId ?: "")
                auth.currentUser?.delete()?.await()
                logout()
            } catch (e: Exception) {
                authState.value = "FAILED: ${e.message}"
            }
        }
    }
}
