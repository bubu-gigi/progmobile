package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Prenotazione

interface PrenotazioneDao {
    suspend fun getPrenotazioneById(id: String): Prenotazione?
    suspend fun getPrenotazioniByUserId(userId: String): List<Prenotazione>
    suspend fun getPrenotazioniByStrutturaId(strutturaId: String): List<Prenotazione>
    suspend fun addPrenotazione(prenotazione: Prenotazione): Boolean
    suspend fun updatePrenotazione(id: String, prenotazione: Prenotazione): Boolean
    suspend fun deletePrenotazione(id: String): Boolean
}
