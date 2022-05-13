package com.example.fixphone.dao

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT *FROM Favorite")
    //karena bakal di run di korutine
    suspend fun getFavorites(): List<Favorite>
    //GET YANG UDAH DILOG
    @Query("SELECT *FROM FAVORITE WHERE id_user")
    fun getFavoriteById(id: Int): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFavorite(favorite: Favorite): Long?

    @Delete
    fun deleteFavorite(favorite: Favorite): Int?
}