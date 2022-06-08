package com.example.fixphone.model

import com.example.fixphone.dao.FavoriteDao
import com.example.fixphone.dao.UserDao

class DatabaseHelper(private  val favoriteDao: FavoriteDao, private val userDao: UserDao) {
    //bagian untuk favorite
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)  = favoriteDao.addFavorite(favoriteEntity)
    suspend fun getFavorite(id_user: Int, image: String): FavoriteEntity = favoriteDao.getFavorite(id_user, image)
    suspend fun getFavoriteById(id_user: Int)= favoriteDao.getAllFavoriteById(id_user)
    suspend fun deleteFavorite(id_user: Int, image: String): Int = favoriteDao.deleteFavorite(id_user,image)

    // User
//    suspend fun checkUsernameExists(username: String): UserEntity = userDao.checkUsernameExists(username)
    suspend fun getALlUser() = userDao.getUser()
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun loginUser(username: String, password: String) = userDao.loginUser(username, password)
    suspend fun updateUser(user: User) = userDao.insertUser(user)
}