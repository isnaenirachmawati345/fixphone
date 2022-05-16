package com.example.fixphone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixphone.dao.UserDao
import com.example.fixphone.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class PhoneDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object{
        private var INSTANCE: PhoneDatabase? =null
        fun getInstance(context: Context): PhoneDatabase? {
            if (INSTANCE == null) {
                synchronized(PhoneDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PhoneDatabase::class.java, "phonespecification.db").fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
        fun destroyInstace(){
            INSTANCE = null
        }
    }
}