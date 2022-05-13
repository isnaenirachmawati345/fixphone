package com.example.fixphone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fixphone.DataStore.DataStoreManager

class HomeViewModelFactory(private val pref : DataStoreManager) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: "+modelClass.name)
    }
}