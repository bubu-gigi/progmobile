package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Conversazione


interface ConversazioneDao {
    suspend fun getConversazioni(): List<Conversazione>
    suspend fun getConversazioniByGiocatoreId(playerId: String): List<Conversazione>
    suspend fun getConversazioniByStrutturaId(strutturaId: String): List<Conversazione>
    suspend fun getConversazione(giocatoreId: String, strutturaId: String): Conversazione?
    suspend fun addOrUpdateConversazione(conversazioneId: Conversazione): Boolean
    suspend fun deleteConversazione(giocatoreId: String, strutturaId: String): Boolean
}