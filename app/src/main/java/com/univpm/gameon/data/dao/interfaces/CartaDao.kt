package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Carta

interface CartaDao {
    suspend fun getCarteByUserId(userId: String): List<Carta>
    suspend fun getCartaById(id: String): Carta?
    suspend fun addCarta(carta: Carta): Boolean
    suspend fun updateCarta(id: String, carta: Carta): Boolean
    suspend fun deleteCarta(id: String): Boolean
    suspend fun deleteCarteByUserId(userId: String): Boolean
}