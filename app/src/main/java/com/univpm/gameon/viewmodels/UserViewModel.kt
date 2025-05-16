package com.univpm.gameon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.data.dao.impl.UserDaoImpl
import com.univpm.gameon.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository(UserDaoImpl())

    fun createUser() {
        val user = User(id = "u123", name = "Mario", email = "mario@email.com")
        viewModelScope.launch {
            val success = userRepository.saveUser(user)
            if (success) {
                println("Utente salvato!")
            } else {
                println("Errore nel salvataggio.")
            }
        }
    }

    fun loadUser(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(id)
            println("Utente trovato: $user")
        }
    }
}
