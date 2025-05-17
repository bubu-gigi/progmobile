package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.User

interface UserRepository {
    suspend fun getUserById(id: String): User?
    suspend fun saveUser(user: User): Boolean
    suspend fun updateUser(id: String, user: User): Boolean
    suspend fun removeUser(id: String): Boolean
}
