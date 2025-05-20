package com.univpm.gameon.core

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.univpm.gameon.data.collections.enums.UserRuolo

fun checkAccess(navController: NavController, requiredRole: UserRuolo? = null) {
    if (!UserSessionManager.isLoggedIn || (requiredRole != null && UserSessionManager.userRole != requiredRole)) {
        navController.navigate(LoginScreenRoute)
    }
}

//viewmodel?
fun logout(navController: NavController) {
    FirebaseAuth.getInstance().signOut()
    UserSessionManager.isLoggedIn = false
    UserSessionManager.userRole = null
    navController.navigate(LoginScreenRoute)
}

fun validateEmail(email: String): String? {
    if (email.isBlank()) return "Il campo email è obbligatorio."
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    if (!emailRegex.matches(email)) return "Inserisci un indirizzo email valido."
    return null
}

fun validatePassword(password: String): String? {
    if (password.isBlank()) return "Il campo password è obbligatorio."
    if (password.length < 6) return "La password deve contenere almeno 6 caratteri."
    if (!password.any { it.isLetter() }) return "La password deve contenere almeno una lettera."
    if (!password.any { it.isDigit() }) return "La password deve contenere almeno un numero."
    return null
}

fun checkFieldLength(fieldValue: String, minLength: Int, fieldName: String): String? {
    return when {
        fieldValue.isBlank() -> "Il campo $fieldName è obbligatorio"
        fieldValue.length < minLength -> "Il campo $fieldName deve contenere almeno $minLength caratteri"
        else -> null
    }
}

fun validateCodiceFiscale(cf: String): String? {
    val regex = Regex("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")
    return when {
        cf.isBlank() -> "Inserisci il codice fiscale"
        !regex.matches(cf.uppercase()) -> "Codice fiscale non valido"
        else -> null
    }
}

