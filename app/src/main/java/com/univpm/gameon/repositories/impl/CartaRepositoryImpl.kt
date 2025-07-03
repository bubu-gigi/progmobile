package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.dao.interfaces.CartaDao
import com.univpm.gameon.repositories.CartaRepository

class CartaRepositoryImpl(private val cartaDao: CartaDao) : CartaRepository {

    override suspend fun getCarteByUserId(userId: String): List<Carta> {
        return cartaDao.getCarteByUserId(userId)
    }

    override suspend fun getCarta(id: String): Carta? {
        return cartaDao.getCartaById(id)
    }

    override suspend fun saveCarta(carta: Carta): Boolean {
        return cartaDao.addCarta(carta)
    }

    override suspend fun updateCarta(id: String, carta: Carta): Boolean {
        return cartaDao.updateCarta(id, carta)
    }

    override suspend fun deleteCarta(id: String): Boolean {
        return cartaDao.deleteCarta(id)
    }

    override suspend fun deleteCarteByUserId(userId: String): Boolean {
        return cartaDao.deleteCarteByUserId(userId)
    }

}
