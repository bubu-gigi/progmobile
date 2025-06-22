package com.univpm.gameon.data.collections

data class User(
    val id: String = "",
    val name: String = "",
    val cognome: String = "",
    val email: String = "",
    val codiceFiscale: String = "",
    val password: String = "",
    val ruolo: String = "Giocatore",
    val preferiti: List<String>  = listOf()
)