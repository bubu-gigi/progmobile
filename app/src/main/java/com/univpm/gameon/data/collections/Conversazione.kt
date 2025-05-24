package com.univpm.gameon.data.collections

import com.google.firebase.Timestamp

data class Conversazione(
    val giocatoreId: String? = "",
    val strutturaId: String? = "",
    val ultimoMessaggio: String? = "",
    val ultimoTimestamp: Timestamp? = null
)