package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.dao.interfaces.PrenotazioneDao
import com.univpm.gameon.repositories.PrenotazioneRepository

class PrenotazioneRepositoryImpl(private val dao: PrenotazioneDao) : PrenotazioneRepository{

    override suspend fun getPrenotazione(id: String): Prenotazione? {
        return dao.getPrenotazioneById(id)
    }

    override suspend fun getPrenotazioniByUser(userId: String): List<Prenotazione> {
        return dao.getPrenotazioniByUserId(userId)
    }

    override suspend fun getPrenotazioniByStruttura(strutturaId: String): List<Prenotazione> {
        return dao.getPrenotazioniByStrutturaId(strutturaId)
    }

    override suspend fun savePrenotazione(prenotazione: Prenotazione): Boolean {
        return dao.addPrenotazione(prenotazione)
    }

    override suspend fun updatePrenotazione(id: String, prenotazione: Prenotazione): Boolean {
        return dao.updatePrenotazione(id, prenotazione)
    }

    override suspend fun deletePrenotazione(id: String): Boolean {
        return dao.deletePrenotazione(id)
    }
}
