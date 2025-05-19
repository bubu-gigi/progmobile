package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Recensione
import com.univpm.gameon.data.dao.interfaces.RecensioneDao
import kotlinx.coroutines.tasks.await

class RecensioneDaoImpl : RecensioneDao {
    private val db = Firebase.firestore
    private val recensioniCollection = db.collection("recensioni")

    override suspend fun getRecensioniByStrutturaId(strutturaId: String): List<Recensione> {
        return try {
            val snapshot = recensioniCollection
                .whereEqualTo("strutturaId", strutturaId)
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(Recensione::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getRecensioneById(id: String): Recensione? {
        return try {
            val snapshot = recensioniCollection.document(id).get().await()
            snapshot.toObject(Recensione::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addRecensione(recensione: Recensione): Boolean {
        return try {
            if (recensione.stelle in 1..5) {
                val docRef = recensioniCollection.document()
                val userWithId = recensione.copy(id = docRef.id)
                docRef.set(userWithId).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateRecensione(id: String, recensione: Recensione): Boolean {
        return try {
            if (recensione.stelle in 1..5) {
                recensioniCollection.document(id).set(recensione).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteRecensione(id: String): Boolean {
        return try {
            recensioniCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
