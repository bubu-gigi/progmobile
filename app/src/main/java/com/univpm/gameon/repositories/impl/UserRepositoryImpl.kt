package com.univpm.gameon.repositories.impl

import com.univpm.gameon.data.collections.User
import com.univpm.gameon.data.dao.interfaces.UserDao
import com.univpm.gameon.repositories.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUserById(id: String): User? {
        return userDao.getUserById(id)
    }

    override suspend fun saveUser(user: User): Boolean {
        return userDao.addUser(user)
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        return userDao.updateUser(id, user)
    }

    override suspend fun removeUser(id: String): Boolean {
        return userDao.deleteUser(id)
    }
}
