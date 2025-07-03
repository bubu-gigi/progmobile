package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Conversazione

interface ConversazioneRepository {
    suspend fun getConversazioni(): List<Conversazione>
    suspend fun getConversazioniByPlayerId(giocatoreId: String): List<Conversazione>
    suspend fun getConversazioniByStructureId(strutturaId: String): List<Conversazione>
    suspend fun getConversazione(giocatoreId: String, strutturaId: String): Conversazione?
    suspend fun saveConversazione(conversazione: Conversazione): Boolean
    suspend fun deleteConversazione(giocatoreId: String, strutturaId: String): Boolean
    suspend fun deleteConversazioniByGiocatoreId(giocatoreId: String): Boolean
}
