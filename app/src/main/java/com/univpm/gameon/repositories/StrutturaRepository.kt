package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.collections.Struttura

interface StrutturaRepository {
    suspend fun getStrutture(): List<Struttura>
    suspend fun getStruttura(id: String): Struttura?
    suspend fun saveStruttura(struttura: Struttura, campi: List<Campo>): Boolean
    suspend fun updateStruttura(id: String, struttura: Struttura): Boolean
    suspend fun deleteStruttura(id: String): Boolean
}
