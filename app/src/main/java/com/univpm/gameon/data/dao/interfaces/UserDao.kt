package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.User

interface UserDao {
    suspend fun getUserById(id: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun addUser(user: User): Boolean
    suspend fun updateUser(id: String, user: User): Boolean
    suspend fun deleteUser(id: String): Boolean
}