package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Messaggio

interface MessaggioDao {
    suspend fun getMessaggi(giocatoreId: String, strutturaId: String): List<Messaggio>
    suspend fun sendMessaggio(giocatoreId: String, strutturaId: String, message: Messaggio): Boolean
    suspend fun deleteMessaggio(giocatoreId: String, strutturaId: String, messaggioId: String): Boolean
}
