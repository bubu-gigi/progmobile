package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.UserRuolo

data class User(
    val name: String = "",
    val cognome: String = "",
    val email: String = "",
    val codiceFiscale: String = "",
    val password: String = "",
    val ruolo: UserRuolo = UserRuolo.Giocatore
)