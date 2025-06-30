package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Prenotazione
import com.univpm.gameon.data.dao.interfaces.PrenotazioneDao
import kotlinx.coroutines.tasks.await

class PrenotazioneDaoImpl : PrenotazioneDao {
    private val db = Firebase.firestore
    private val prenotazioniCollection = db.collection("prenotazioni")

    override suspend fun getPrenotazioneById(id: String): Prenotazione? {
        return try {
            val snapshot = prenotazioniCollection.document(id).get().await()
            snapshot.toObject(Prenotazione::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getPrenotazioniByUserId(userId: String): List<Prenotazione> {
        return try {
            val snapshot = prenotazioniCollection
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Prenotazione::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPrenotazioni(): List<Prenotazione> {
        return try {
            val snapshot = prenotazioniCollection
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Prenotazione::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addPrenotazione(prenotazione: Prenotazione): Boolean {
        return try {
            val docRef = prenotazioniCollection.document()
            val prenotazioneWithId = prenotazione.copy(id = docRef.id)
            docRef.set(prenotazioneWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updatePrenotazione(id: String, prenotazione: Prenotazione): Boolean {
        return try {
            prenotazioniCollection.document(id).set(prenotazione).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deletePrenotazione(id: String): Boolean {
        return try {
            prenotazioniCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
