package com.univpm.gameon.data.collections

data class Struttura(
    val adminId: String = "",
    val nome: String = "",
    val indirizzo: String = "",
    val latitudine: Double = 0.0,
    val longitudine: Double = 0.0,
    val sportPraticabili: List<String> = listOf(),
    val campi: List<Campo> = listOf(),
)