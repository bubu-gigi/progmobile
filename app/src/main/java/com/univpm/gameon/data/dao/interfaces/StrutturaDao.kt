package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura

interface StrutturaDao {
    suspend fun getStrutture(): List<Struttura>
    suspend fun getStrutturaById(id: String):  Pair<Struttura, List<Campo>>
    suspend fun addStruttura(struttura: Struttura, campi: List<Campo>): Boolean
    suspend fun updateStruttura(id: String, struttura: Struttura, campi: List<Campo>): Boolean
    suspend fun deleteStruttura(id: String): Boolean
}