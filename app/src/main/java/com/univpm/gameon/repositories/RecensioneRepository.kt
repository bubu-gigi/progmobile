package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Recensione

interface RecensioneRepository {
    suspend fun getRecensione(id: String): Recensione?
    suspend fun getRecensioniByStruttura(strutturaId: String): List<Recensione>
    suspend fun saveRecensione(recensione: Recensione): Boolean
    suspend fun updateRecensione(id: String, recensione: Recensione): Boolean
    suspend fun deleteRecensione(strutturaId: String, utenteId: String): Boolean
}
