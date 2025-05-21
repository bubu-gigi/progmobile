package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Messaggio

interface MessaggioRepository {
    suspend fun getMessaggi(giocatoreId: String, strutturaId: String): List<Messaggio>
    suspend fun sendMessaggio(giocatoreId: String, strutturaId: String, messaggio: Messaggio): Boolean
    suspend fun deleteMessaggio(giocatoreId: String, strutturaId: String, messaggioId: String): Boolean
}
