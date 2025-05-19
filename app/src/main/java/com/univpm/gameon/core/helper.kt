package com.univpm.gameon.core

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.univpm.gameon.data.collections.enums.UserRuolo

fun checkAccess(navController: NavController, requiredRole: UserRuolo? = null) {
    if (!UserSessionManager.isLoggedIn || (requiredRole != null && UserSessionManager.userRole != requiredRole)) {
        navController.navigate(LoginScreenRoute)
    }
}

fun logout(navController: NavController) {
    FirebaseAuth.getInstance().signOut()
    UserSessionManager.isLoggedIn = false
    UserSessionManager.userRole = null
    navController.navigate(LoginScreenRoute)
}
