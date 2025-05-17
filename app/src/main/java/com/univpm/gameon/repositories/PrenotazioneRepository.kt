package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Prenotazione

interface PrenotazioneRepository {
    suspend fun getPrenotazione(id: String): Prenotazione?
    suspend fun getPrenotazioniByUser(userId: String): List<Prenotazione>
    suspend fun getPrenotazioniByStruttura(strutturaId: String): List<Prenotazione>
    suspend fun savePrenotazione(prenotazione: Prenotazione): Boolean
    suspend fun updatePrenotazione(id: String, prenotazione: Prenotazione): Boolean
    suspend fun deletePrenotazione(id: String): Boolean
}
