package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.data.dao.interfaces.ConversazioneDao
import com.univpm.gameon.repositories.ConversazioneRepository

class ConversazioneRepositoryImpl(private val dao: ConversazioneDao) : ConversazioneRepository {

    override suspend fun getConversazioni(): List<Conversazione> {
        return dao.getConversazioni();
    }
    override suspend fun getConversazioniByPlayerId(giocatoreId: String): List<Conversazione> {
        return dao.getConversazioniByGiocatoreId(giocatoreId);
    }

    override suspend fun getConversazioniByStructureId(strutturaId: String): List<Conversazione> {
        return dao.getConversazioniByStrutturaId(strutturaId);
    }

    override suspend fun getConversazione(giocatoreId: String, strutturaId: String): Conversazione? {
        return dao.getConversazione(giocatoreId, strutturaId)
    }

    override suspend fun saveConversazione(conversazione: Conversazione): Boolean {
        return dao.addOrUpdateConversazione(conversazione)
    }

    override suspend fun deleteConversazione(giocatoreId: String, strutturaId: String): Boolean {
        return dao.deleteConversazione(giocatoreId, strutturaId)
    }
}
