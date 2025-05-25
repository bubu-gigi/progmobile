package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.dao.interfaces.StrutturaDao
import com.univpm.gameon.repositories.StrutturaRepository

class StrutturaRepositoryImpl(private val strutturaDao: StrutturaDao) : StrutturaRepository {
    override suspend fun getStrutture(): List<Struttura> {
        return strutturaDao.getStrutture()
    }

    override suspend fun getStruttura(id: String): Struttura? {
        return strutturaDao.getStrutturaById(id)
    }

    override suspend fun saveStruttura(struttura: Struttura, campi: List<Campo>): Boolean {
        return strutturaDao.addStruttura(struttura, campi)
    }

    override suspend fun updateStruttura(id: String, struttura: Struttura): Boolean {
        return strutturaDao.updateStruttura(id, struttura)
    }

    override suspend fun deleteStruttura(id: String): Boolean {
        return strutturaDao.deleteStruttura(id)
    }
}
