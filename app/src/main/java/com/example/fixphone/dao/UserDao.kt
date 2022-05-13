package com.example.fixphone.dao

import androidx.room.*
import com.example.fixphone.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun loginUser(username: String, password: String): Boolean
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): User
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long
    @Update
    fun updateUser(user: User): Int
}