package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Messaggio
import com.univpm.gameon.data.dao.interfaces.MessaggioDao
import com.univpm.gameon.repositories.MessaggioRepository

class MessaggioRepositoryImpl(private val dao: MessaggioDao) : MessaggioRepository {

    override suspend fun getMessaggi(playerId: String, structureId: String): List<Messaggio> {
        return dao.getMessaggi(playerId, structureId)
    }

    override suspend fun sendMessaggio(playerId: String, structureId: String, messaggio: Messaggio): Boolean {
        return dao.sendMessaggio(playerId, structureId, messaggio)
    }

    override suspend fun deleteMessaggio(playerId: String, structureId: String, messageId: String): Boolean {
        return dao.deleteMessaggio(playerId, structureId, messageId)
    }
}
