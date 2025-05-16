package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Struttura

interface StrutturaDao {
    suspend fun getStrutturaById(id: String): Struttura?
    suspend fun addStruttura(struttura: Struttura): Boolean
    suspend fun updateStruttura(id: String, struttura: Struttura): Boolean
    suspend fun deleteStruttura(id: String): Boolean
}