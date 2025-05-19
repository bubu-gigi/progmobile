package com.univpm.gameon.core

import com.univpm.gameon.data.collections.enums.UserRuolo

object UserSessionManager {
    var isLoggedIn: Boolean = false
    var userRole: UserRuolo? = null

    fun clear() {
        isLoggedIn = false
        userRole = null
    }
}