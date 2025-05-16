package com.univpm.gameon.data.collections

data class Prenotazione(
    val userId: String = "",
    val strutturaId: String = "",
    val campoId: String = "",
    val data: String = "",
    val orarioInizio: String = "",
    val durataOre: Int = 1,
    val pubblica: Boolean = false
)