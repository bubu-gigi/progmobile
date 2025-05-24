package com.univpm.gameon.data.collections

import com.google.firebase.Timestamp

data class Conversazione(
    val giocatoreId: String = "",
    val strutturaId: String = "",
    val strutturaNome: String = "",
    val ultimoMessaggio: String? = "",
    val ultimoTimestamp: Timestamp? = null
)