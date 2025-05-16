package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.dao.interfaces.CartaDao

class CartaRepository(private val cartaDao: CartaDao) {

    suspend fun getCarteByUserId(userId: String): List<Carta> {
        return cartaDao.getCarteByUserId(userId)
    }

    suspend fun getCarta(id: String): Carta? {
        return cartaDao.getCartaById(id)
    }

    suspend fun saveCarta(carta: Carta): Boolean {
        return cartaDao.addCarta(carta)
    }

    suspend fun updateCarta(id: String, carta: Carta): Boolean {
        return cartaDao.updateCarta(id, carta)
    }

    suspend fun deleteCarta(id: String): Boolean {
        return cartaDao.deleteCarta(id)
    }
}
