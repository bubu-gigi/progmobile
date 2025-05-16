package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.dao.interfaces.PrenotazioneDao

class PrenotazioneRepository(private val dao: PrenotazioneDao) {

    suspend fun getPrenotazione(id: String): Prenotazione? {
        return dao.getPrenotazioneById(id)
    }

    suspend fun getPrenotazioniByUser(userId: String): List<Prenotazione> {
        return dao.getPrenotazioniByUserId(userId)
    }

    suspend fun getPrenotazioniByStruttura(strutturaId: String): List<Prenotazione> {
        return dao.getPrenotazioniByStrutturaId(strutturaId)
    }

    suspend fun savePrenotazione(prenotazione: Prenotazione): Boolean {
        return dao.addPrenotazione(prenotazione)
    }

    suspend fun updatePrenotazione(id: String, prenotazione: Prenotazione): Boolean {
        return dao.updatePrenotazione(id, prenotazione)
    }

    suspend fun deletePrenotazione(id: String): Boolean {
        return dao.deletePrenotazione(id)
    }
}
