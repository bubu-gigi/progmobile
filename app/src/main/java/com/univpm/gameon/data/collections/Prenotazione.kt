package com.univpm.gameon.data.collections

data class Prenotazione(
    val id: String = "",
    val userId: String = "",
    val strutturaId: String = "",
    val campoId: String = "",
    val data: String = "",
    val orarioInizio: String = "",
    val orarioFine: String = "",
    val pubblica: Boolean = false
)
