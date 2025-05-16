package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.dao.interfaces.CampoDao

class CampoRepository(private val campoDao: CampoDao) {

    suspend fun getCampiByStrutturaId(strutturaId: String): List<Campo> {
        return campoDao.getCampiByStrutturaId(strutturaId)
    }

    suspend fun getCampo(id: String): Campo? {
        return campoDao.getCampoById(id)
    }

    suspend fun saveCampo(campo: Campo): Boolean {
        return campoDao.addCampo(campo)
    }

    suspend fun updateCampo(id: String, campo: Campo): Boolean {
        return campoDao.updateCampo(id, campo)
    }

    suspend fun deleteCampo(id: String): Boolean {
        return campoDao.deleteCampo(id)
    }
}
