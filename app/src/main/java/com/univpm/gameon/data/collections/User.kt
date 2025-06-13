package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.UserRuolo

data class User(
    val id: String = "",
    val name: String = "",
    val cognome: String = "",
    val email: String = "",
    val codiceFiscale: String = "",
    val password: String = "",
    val ruolo: UserRuolo = UserRuolo.GIOCATORE,
    val preferiti: List<String>  = listOf()
)