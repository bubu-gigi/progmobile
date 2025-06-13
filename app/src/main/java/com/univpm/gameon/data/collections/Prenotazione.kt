package com.univpm.gameon.data.collections

data class Prenotazione(
    val id: String = "",
    val userId: String = "",
    val strutturaId: String = "",
    val campoId: String = "",
    val data: String = "",
    val orari: String = "", // es. "08:00-09:00,10:00-11:00"
    val pubblica: Boolean = false
)
