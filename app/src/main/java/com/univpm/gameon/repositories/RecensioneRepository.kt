package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.data.dao.interfaces.RecensioneDao

class RecensioneRepository(private val recensioneDao: RecensioneDao) {

    suspend fun getRecensione(id: String): Recensione? {
        return recensioneDao.getRecensioneById(id)
    }

    suspend fun getRecensioniByStruttura(strutturaId: String): List<Recensione> {
        return recensioneDao.getRecensioniByStrutturaId(strutturaId)
    }

    suspend fun saveRecensione(recensione: Recensione): Boolean {
        return recensioneDao.addRecensione(recensione)
    }

    suspend fun updateRecensione(id: String, recensione: Recensione): Boolean {
        return recensioneDao.updateRecensione(id, recensione)
    }

    suspend fun deleteRecensione(id: String): Boolean {
        return recensioneDao.deleteRecensione(id)
    }
}
