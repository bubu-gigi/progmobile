package com.univpm.gameon.core

object UserSessionManager {
    var isLoggedIn: Boolean = false
    var userRole: String? = null
    var userId: String? = null
    var userNome: String? = null
    var userCognome: String? = null

    fun clear() {
        isLoggedIn = false
        userRole = null
        userId = null
        userNome = null
        userCognome = null
    }
}