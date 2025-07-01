package com.univpm.gameon.data.collections

data class Prenotazione(
    val id: String = "",
    val userId: String = "",
    val strutturaId: String = "",
    val campoId: String = "",
    val strutturaNome: String = "",
    val campoNome: String = "",
    val data: String = "",
    val orarioInizio: String = "",
    val orarioFine: String = ""
)
