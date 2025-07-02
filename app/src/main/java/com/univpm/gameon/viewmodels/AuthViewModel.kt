package com.univpm.gameon.viewmodels

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _authState = MutableStateFlow<String?>(null)
    val authState: StateFlow<String?> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _destination = MutableStateFlow<Any?>(null)
    var destination: StateFlow<Any?> = _destination.asStateFlow()

    fun loadCurrentUser() {
        viewModelScope.launch {
            val email = auth.currentUser?.email ?: return@launch
            val user = userRepository.getUserByEmail(email)
            _currentUser.value = user
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                val user = userRepository.getUserByEmail(email)
                UserSessionManager.isLoggedIn = true
                UserSessionManager.userRole = user?.ruolo
                UserSessionManager.userId = user?.id
                UserSessionManager.userNome = user?.name
                UserSessionManager.userCognome = user?.cognome

                _destination.value = when (user?.ruolo) {
                    "Admin" -> AdminHomeScreenRoute
                    "Giocatore" -> GiocatoreHomeScreenRoute
                    else -> LoginScreenRoute
                }

                _authState.value = "SUCCESS"
                _currentUser.value = user
            } catch (e: Exception) {
                _authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
                userRepository.saveUser(user)
                _authState.value = "SUCCESS"
            } catch (e: Exception) {
                _authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun logout() {
        auth.signOut()
        UserSessionManager.clear()
        _authState.value = null
        _destination.value = LoginScreenRoute
        _currentUser.value = null
    }

    fun updateProfile(id: String, updatedUser: User) {
        viewModelScope.launch {
            try {
                val firebaseUser = auth.currentUser
                if (firebaseUser == null) {
                    _authState.value = "FAILED: Nessun utente autenticato"
                    return@launch
                }

                if (firebaseUser.email != updatedUser.email) {
                    firebaseUser.updateEmail(updatedUser.email).await()
                }

                firebaseUser.updatePassword(updatedUser.password).await()

                userRepository.updateUser(id, updatedUser)

                UserSessionManager.userRole = updatedUser.ruolo
                UserSessionManager.userId = updatedUser.id

                _authState.value = "UPDATED"
                _currentUser.value = updatedUser
            } catch (e: Exception) {
                _authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                userRepository.removeUser(UserSessionManager.userId ?: "")
                auth.currentUser?.delete()?.await()
                logout()
            } catch (e: Exception) {
                _authState.value = "FAILED: ${e.message}"
            }
        }
    }

    fun clearDestination() {
        _destination.value = null
    }
}
