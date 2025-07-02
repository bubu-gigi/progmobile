package com.univpm.gameon.core

import com.univpm.gameon.data.collections.User

object UserSessionManager {
    var isLoggedIn: Boolean = false
    private var currentUser: User? = null

    val userId: String?
        get() = currentUser?.id

    val userRole: String?
        get() = currentUser?.ruolo

    val userNome: String?
        get() = currentUser?.name

    val userCognome: String?
        get() = currentUser?.cognome

    val userPreferiti: List<String>
        get() = currentUser?.preferiti ?: emptyList()

    fun setCurrentUser(user: User) {
        currentUser = user
        isLoggedIn = true
    }

    fun getCurrentUser(): User? = currentUser

    fun clear() {
        isLoggedIn = false
        currentUser = null
    }
}
