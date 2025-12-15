package com.example.mobile_prototype_todo.data.repository

import com.example.mobile_prototype_todo.data.local.dao.UserDao
import com.example.mobile_prototype_todo.data.model.User

class AuthRepository(private val userDao: UserDao) {
    
    suspend fun login(username: String, password: String): Result<User> {
        return try {
            val user = userDao.login(username, password)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Usuário ou senha inválidos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun register(username: String, password: String, name: String): Result<User> {
        return try {
            // Verifica se o usuário já existe
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                return Result.failure(Exception("Usuário já existe"))
            }
            
            val newUser = User(username = username, password = password, name = name)
            val userId = userDao.insertUser(newUser)
            val user = userDao.getUserById(userId.toInt())
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Erro ao criar usuário"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
