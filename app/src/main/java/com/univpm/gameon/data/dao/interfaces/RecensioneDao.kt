package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Recensione

interface RecensioneDao {
    suspend fun getRecensioniByStrutturaId(strutturaId: String): List<Recensione>
    suspend fun getRecensioneById(id: String): Recensione?
    suspend fun addRecensione(recensione: Recensione): Boolean
    suspend fun updateRecensione(id: String, recensione: Recensione): Boolean
    suspend fun deleteRecensione(id: String): Boolean
}