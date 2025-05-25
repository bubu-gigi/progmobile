package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Campo
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

    override suspend fun getStrutturaById(id: String): Pair<Struttura, List<Campo>> {
        return try {
            val docRef = struttureCollection.document(id)
            val snapshot = docRef.get().await()
            val struttura = snapshot.toObject(Struttura::class.java)!!.copy(id = snapshot.id)

            val campiSnapshot = docRef.collection("campi").get().await()
            val campi = campiSnapshot.documents.mapNotNull { it.toObject(Campo::class.java)?.copy(id = it.id) }

            Pair(struttura, campi)
        } catch (e: Exception) {
            throw e
        }
    }



    override suspend fun addStruttura(struttura: Struttura, campi: List<Campo>): Boolean {
        return try {
            val docRef = struttureCollection.document()
            val strutturaWithId = struttura.copy(id = docRef.id)
            docRef.set(strutturaWithId).await()

            campi.forEach { campo ->
                val campoDoc = docRef.collection("campi").document()
                val campoWithId = campo.copy(id = campoDoc.id)
                campoDoc.set(campoWithId).await()
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateStruttura(id: String, struttura: Struttura, campi: List<Campo>): Boolean {
        return try {
            val docRef = struttureCollection.document(id)

            val strutturaSenzaCampi = struttura.copy(id = id)
            docRef.set(strutturaSenzaCampi).await()

            val campiCollection = docRef.collection("campi")
            val existingCampi = campiCollection.get().await()
            for (campoDoc in existingCampi.documents) {
                campoDoc.reference.delete().await()
            }

            campi.forEach { campo ->
                val campoDoc = campiCollection.document()
                val campoWithId = campo.copy(id = campoDoc.id)
                campoDoc.set(campoWithId).await()
            }

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
