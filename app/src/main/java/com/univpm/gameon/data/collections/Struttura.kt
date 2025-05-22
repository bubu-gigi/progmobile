package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.Sport

data class Struttura(
    val id: String = "",
    val nome: String = "",
    val indirizzo: String = "",
    val citta: String = "",
    val latitudine: Double = 0.0,
    val longitudine: Double = 0.0,
    val sportPraticabili: List<Sport> = listOf(),
    val campi: List<Campo> = listOf(),
)