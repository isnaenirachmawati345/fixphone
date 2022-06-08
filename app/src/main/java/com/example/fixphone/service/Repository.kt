package com.example.fixphone.service

import com.example.fixphone.DataStore.DataStoreManager
import com.example.fixphone.model.DatabaseHelper
import com.example.fixphone.model.FavoriteEntity
import com.example.fixphone.model.User
import kotlinx.coroutines.flow.Flow

class Repository(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper, private val dataStoreManager: DataStoreManager) {
    //Retrofit
    suspend fun getLatesPhone() = apiHelper.getLatestPhone()
    suspend fun getSpecsById(slug: String): List<Any> = listOf(apiHelper.getSpecsPhoneById(slug))

    //Database
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) = databaseHelper.addFavorite(favoriteEntity)
    suspend fun getFavorite(id_user: Int, country_name: String): FavoriteEntity = databaseHelper.getFavorite(id_user, country_name)
    suspend fun getAllFavoriteById(id_user: Int): List<FavoriteEntity> = databaseHelper.getFavoriteById(id_user)
    suspend fun deleteFavorite(id_user: Int, country_name: String): Int = databaseHelper.deleteFavorite(id_user, country_name)
    // DataStore
    suspend fun setUserPref(userEntity: User) = dataStoreManager.setUser(userEntity)
    suspend fun getUserPref(): Flow<User> = dataStoreManager.getUser()
    suspend fun deleteUserPref() = dataStoreManager.deleteUserFromPref()
    // User
//    suspend fun checkUsernameExists(username: String): UserEntity = databaseHelper.checkUsernameExists(username)
    suspend fun getAllUser() = databaseHelper.getALlUser()
    suspend fun insertUser(userEntity: User) = databaseHelper.insertUser(userEntity)
    suspend fun loginUser(username: String, password: String) = databaseHelper.loginUser(username, password)
    suspend fun updateUser(userEntity: User) = databaseHelper.updateUser(userEntity)
}