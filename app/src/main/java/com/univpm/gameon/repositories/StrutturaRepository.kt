package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.dao.interfaces.StrutturaDao

class StrutturaRepository(private val strutturaDao: StrutturaDao) {

    suspend fun getStruttura(id: String): Struttura? {
        return strutturaDao.getStrutturaById(id)
    }

    suspend fun saveStruttura(struttura: Struttura): Boolean {
        return strutturaDao.addStruttura(struttura)
    }

    suspend fun updateStruttura(id: String, struttura: Struttura): Boolean {
        return strutturaDao.updateStruttura(id, struttura)
    }

    suspend fun deleteStruttura(id: String): Boolean {
        return strutturaDao.deleteStruttura(id)
    }
}
