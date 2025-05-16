package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Campo
import com.univpm.gameon.data.dao.interfaces.CampoDao
import kotlinx.coroutines.tasks.await

class CampoDaoImpl : CampoDao {
    private val db = Firebase.firestore
    private val campiCollection = db.collection("campi")

    override suspend fun getCampiByStrutturaId(strutturaId: String): List<Campo> {
        return try {
            val snapshot = campiCollection
                .whereEqualTo("strutturaId", strutturaId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Campo::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCampoById(id: String): Campo? {
        return try {
            val snapshot = campiCollection.document(id).get().await()
            snapshot.toObject(Campo::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addCampo(campo: Campo): Boolean {
        return try {
            campiCollection.add(campo).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateCampo(id: String, campo: Campo): Boolean {
        return try {
            campiCollection.document(id).set(campo).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteCampo(id: String): Boolean {
        return try {
            campiCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
