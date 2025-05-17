package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.dao.interfaces.CampoDao
import com.univpm.gameon.repositories.CampoRepository

class CampoRepositoryImpl(private val campoDao: CampoDao) : CampoRepository {

    override suspend fun getCampiByStrutturaId(strutturaId: String): List<Campo> {
        return campoDao.getCampiByStrutturaId(strutturaId)
    }

    override suspend fun getCampo(id: String): Campo? {
        return campoDao.getCampoById(id)
    }

    override suspend fun saveCampo(campo: Campo): Boolean {
        return campoDao.addCampo(campo)
    }

    override suspend fun updateCampo(id: String, campo: Campo): Boolean {
        return campoDao.updateCampo(id, campo)
    }

    override suspend fun deleteCampo(id: String): Boolean {
        return campoDao.deleteCampo(id)
    }
}
