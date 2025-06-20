package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.univpm.gameon.data.collections.Conversazione
import com.univpm.gameon.data.dao.interfaces.ConversazioneDao
import kotlinx.coroutines.tasks.await

class ConversazioneDaoImpl : ConversazioneDao {
    private val db = FirebaseFirestore.getInstance()
    private val conversationsCollection = db.collection("conversazioni")

    override suspend fun getConversazioni(): List<Conversazione> {
        val snapshot = conversationsCollection.get().await()
        return snapshot.toObjects(Conversazione::class.java)
    }

    override suspend fun getConversazioniByGiocatoreId(giocatoreId: String): List<Conversazione> {
        val snapshot = conversationsCollection
            .whereEqualTo("giocatoreId", giocatoreId)
            .get()
            .await()
        return snapshot.toObjects(Conversazione::class.java)
    }

    override suspend fun getConversazioniByStrutturaId(strutturaId: String): List<Conversazione> {
        val snapshot = conversationsCollection
            .whereEqualTo("strutturaId", strutturaId)
            .get()
            .await()
        return snapshot.toObjects(Conversazione::class.java)
    }

    override suspend fun getConversazione(giocatoreId: String, strutturaId: String): Conversazione? {
        val docId = "conversation_${giocatoreId}_$strutturaId"
        val doc = conversationsCollection.document(docId).get().await()
        return doc.toObject(Conversazione::class.java)
    }

    override suspend fun addOrUpdateConversazione(conversazione: Conversazione): Boolean {
        val docId = "conversation_${conversazione.giocatoreId}_${conversazione.strutturaId}"
        return try {
            conversationsCollection.document(docId).set(conversazione).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteConversazione(giocatoreId: String, strutturaId: String): Boolean {
        val docId = "conversation_${giocatoreId}_$strutturaId"
        return try {
            conversationsCollection.document(docId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
