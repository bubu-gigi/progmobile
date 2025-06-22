package com.univpm.gameon.core

object UserSessionManager {
    var isLoggedIn: Boolean = false
    var userRole: String? = null
    var userId: String? = null

    fun clear() {
        isLoggedIn = false
        userRole = null
        userId = null
    }
}