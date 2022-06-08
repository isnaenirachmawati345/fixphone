package com.example.fixphone.database

import com.example.fixphone.dao.FavoriteDao
import com.example.fixphone.dao.UserDao
import com.example.fixphone.model.FavoriteEntity
import com.example.fixphone.model.User

class DatabaseHelper(private val favoriteDao: FavoriteDao, private val userDao: UserDao) {
    // Favorite
    suspend fun addFavorite(favoriteEntity: FavoriteEntity) = favoriteDao.addFavorite(favoriteEntity)
    suspend fun getFavorite(id_user: Int, country_name: String): FavoriteEntity = favoriteDao.getFavorite(id_user, country_name)
    suspend fun getAllFavoriteById(id_user: Int): List<FavoriteEntity> = favoriteDao.getAllFavoriteById(id_user)
    suspend fun deleteFavorite(id_user: Int, country_name: String): Int = favoriteDao.deleteFavorite(id_user, country_name)

    // User
//    suspend fun checkUsernameExists(username: String): UserEntity = userDao.checkUsernameExists(username)
    suspend fun getAllUser() = userDao.getUser()
    suspend fun insertUser(userEntity: User) = userDao.insertUser(userEntity)
    suspend fun loginUser(username: String, password: String) = userDao.loginUser(username, password)
    suspend fun updateUser(userEntity: User) = userDao.insertUser(userEntity)
}