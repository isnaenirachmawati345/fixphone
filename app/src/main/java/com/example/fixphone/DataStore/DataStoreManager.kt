package com.example.fixphone.DataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fixphone.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreManager (private val context: Context){
    companion object {
        const val PREFUSER = "PREF_USER"
        private  val ID_KEY = intPreferencesKey("ID_KEY")
        private val NAMA_KEY = stringPreferencesKey("NAMA_KEY")
        private val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        val USERNAME_KEY = stringPreferencesKey("USERNAME_KEY")
        private val PASSWORD_KEY = stringPreferencesKey("PASSWORD_KEY")
        //untuk cek data ketika login
        const val DEFAULT_ID = -1
        const val DEFAULT_USERNAME = "DEF_USERNAME"
        const val DEFAULT_EMAIL = "DEF_EMAIL"
        const val DEFAULR_NAMA = "DEF_NAMA"
        const val  DEFAULT_PASSWORD = "DEF_PASSWORD"
        val Context.dataStore by preferencesDataStore(DataStoreManager.PREFUSER)
    }
    suspend fun setUser(user: User){
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id_user!!.toInt()
            preferences[NAMA_KEY] = user.nama
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[PASSWORD_KEY] = user.password
        }
    }
    fun getUser(): Flow<User> {
        return context.dataStore.data.map { preferences ->
            User(
                preferences[ID_KEY] ?: DEFAULT_ID,
                preferences[NAMA_KEY] ?: DEFAULR_NAMA,
                 preferences[USERNAME_KEY] ?: DEFAULT_USERNAME,
                preferences[EMAIL_KEY] ?: DEFAULT_EMAIL,
                preferences[PASSWORD_KEY] ?: DEFAULT_PASSWORD
            )

        }
    }
    suspend fun deleteUserFromPref(){
        context.dataStore.edit {
            it.clear()
        }
    }
}