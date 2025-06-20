package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Carta
import com.univpm.gameon.data.dao.interfaces.CartaDao
import kotlinx.coroutines.tasks.await

class CartaDaoImpl : CartaDao {
    private val db = Firebase.firestore
    private val carteCollection = db.collection("carte")

    override suspend fun getCarteByUserId(userId: String): List<Carta> {
        return try {
            val snapshot = carteCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Carta::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCartaById(id: String): Carta? {
        return try {
            val snapshot = carteCollection.document(id).get().await()
            snapshot.toObject(Carta::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addCarta(carta: Carta): Boolean {
        return try {
            val docRef = carteCollection.document()
            val cartaWithId = carta.copy(id = docRef.id)
            docRef.set(cartaWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateCarta(id: String, carta: Carta): Boolean {
        return try {
            carteCollection.document(id).set(carta).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteCarta(id: String): Boolean {
        return try {
            carteCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
