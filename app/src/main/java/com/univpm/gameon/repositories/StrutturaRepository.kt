package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura

interface StrutturaRepository {
    suspend fun getStrutture(): List<Struttura>
    suspend fun getStruttura(id: String):  Pair<Struttura, List<Campo>>
    suspend fun saveStruttura(struttura: Struttura, campi: List<Campo>): Boolean
    suspend fun updateStruttura(id: String, struttura: Struttura, campi: List<Campo>): Boolean
    suspend fun deleteStruttura(id: String): Boolean
}
