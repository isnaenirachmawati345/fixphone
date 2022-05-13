package com.example.fixphone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fixphone.DataStore.DataStoreManager
import com.example.fixphone.model.User

class HomeViewModel(private val pref:DataStoreManager): ViewModel() {
    suspend fun setDataUser(user: User) {
        pref.setUser(user)
    }

    fun getDataUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
}