package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.dao.interfaces.StrutturaDao
import kotlinx.coroutines.tasks.await

class StrutturaDaoImpl : StrutturaDao {
    private val db = Firebase.firestore
    private val struttureCollection = db.collection("strutture")

    override suspend fun getStrutture(): List<Struttura> {
        return try {
            val snapshot = db.collection("strutture")
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Struttura::class.java)?.copy(id = it.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getStrutturaById(id: String): Struttura? {
        return try {
            val snapshot = struttureCollection.document(id).get().await()
            snapshot.toObject(Struttura::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addStruttura(struttura: Struttura): Boolean {
        return try {
            val docRef = struttureCollection.document()
            val userWithId = struttura.copy(id = docRef.id)
            docRef.set(userWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateStruttura(id: String, struttura: Struttura): Boolean {
        return try {
            struttureCollection.document(id).set(struttura).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteStruttura(id: String): Boolean {
        return try {
            struttureCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
