package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.univpm.gameon.data.collections.Messaggio
import com.univpm.gameon.data.dao.interfaces.MessaggioDao
import kotlinx.coroutines.tasks.await

class MessaggioDaoImpl : MessaggioDao {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun getMessaggi(giocatoreId: String, strutturaId: String): List<Messaggio> {
        val convoId = "conversation_${giocatoreId}_$strutturaId"
        val snapshot = db.collection("messages")
            .document(convoId)
            .collection("messages")
            .orderBy("timestamp")
            .get()
            .await()
        return snapshot.toObjects(Messaggio::class.java)
    }

    override suspend fun sendMessaggio(giocatoreId: String, strutturaId: String, messaggio: Messaggio): Boolean {
        val convoId = "conversation_${giocatoreId}_$strutturaId"
        return try {
            val db = FirebaseFirestore.getInstance()

            val strutturaDoc = db.collection("strutture")
                .document(strutturaId)
                .get()
                .await()

            val strutturaNome = strutturaDoc.getString("nome") ?: "Struttura"

            db.collection("messages")
                .document(convoId)
                .collection("messages")
                .add(messaggio)
                .await()

            val conversazione = hashMapOf(
                "giocatoreId" to giocatoreId,
                "strutturaId" to strutturaId,
                "strutturaNome" to strutturaNome,
                "ultimoMessaggio" to messaggio.testo,
                "ultimoTimestamp" to messaggio.timestamp
            )

            db.collection("conversazioni")
                .document(convoId)
                .set(conversazione)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }



    override suspend fun deleteMessaggio(giocatoreId: String, strutturaId: String, messaggioId: String): Boolean {
        val convoId = "conversation_${giocatoreId}_$strutturaId"
        return try {
            db.collection("messages")
                .document(convoId)
                .collection("messages")
                .document(messaggioId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
