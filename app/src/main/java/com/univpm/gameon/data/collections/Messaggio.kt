package com.univpm.gameon.data.collections

import com.google.firebase.Timestamp

data class Messaggio(
    val mittente: String = "",
    val testo: String = "",
    val timestamp: Timestamp? = null
)