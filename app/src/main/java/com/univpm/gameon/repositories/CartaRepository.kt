package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Carta

interface CartaRepository {
    suspend fun getCarteByUserId(userId: String): List<Carta>
    suspend fun getCarta(id: String): Carta?
    suspend fun saveCarta(carta: Carta): Boolean
    suspend fun updateCarta(id: String, carta: Carta): Boolean
    suspend fun deleteCarta(id: String): Boolean
}
