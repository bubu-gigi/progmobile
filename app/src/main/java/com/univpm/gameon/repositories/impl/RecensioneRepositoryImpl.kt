package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.data.dao.interfaces.RecensioneDao
import com.univpm.gameon.repositories.RecensioneRepository

class RecensioneRepositoryImpl(private val recensioneDao: RecensioneDao) : RecensioneRepository {

    override suspend fun getRecensione(id: String): Recensione? {
        return recensioneDao.getRecensioneById(id)
    }

    override suspend fun getRecensioniByStruttura(strutturaId: String): List<Recensione> {
        return recensioneDao.getRecensioniByStrutturaId(strutturaId)
    }

    override suspend fun saveRecensione(recensione: Recensione): Boolean {
        return recensioneDao.addRecensione(recensione)
    }

    override suspend fun updateRecensione(id: String, recensione: Recensione): Boolean {
        return recensioneDao.updateRecensione(id, recensione)
    }

    override suspend fun deleteRecensione(id: String): Boolean {
        return recensioneDao.deleteRecensione(id)
    }
}
