package com.univpm.gameon.data.dao.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.User
import com.univpm.gameon.data.dao.interfaces.UserDao
import kotlinx.coroutines.tasks.await

class UserDaoImpl : UserDao {
    private val db = Firebase.firestore
    private val usersCollection = db.collection("users")

    override suspend fun getUserById(id: String): User? {
        return try {
            val snapshot = usersCollection.document(id).get().await()
            snapshot.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return try {
            val snapshot = usersCollection
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .await()
            if (!snapshot.isEmpty) {
                snapshot.documents[0].toObject(User::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addUser(user: User): Boolean {
        return try {
            val docRef = usersCollection.document()
            val userWithId = user.copy(id = docRef.id)
            docRef.set(userWithId).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        return try {
            usersCollection.document(id).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteUser(id: String): Boolean {
        return try {
            usersCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

}
