package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.User
import com.univpm.gameon.data.dao.interfaces.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun getUser(id: String): User? {
        return userDao.getUserById(id)
    }

    suspend fun saveUser(user: User): Boolean {
        return userDao.addUser(user)
    }

    suspend fun updateUser(id: String, user: User): Boolean {
        return userDao.updateUser(id, user)
    }

    suspend fun removeUser(id: String): Boolean {
        return userDao.deleteUser(id)
    }
}
