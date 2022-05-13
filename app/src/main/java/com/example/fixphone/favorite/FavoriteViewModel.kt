package com.example.fixphone.favorite

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fixphone.dao.Favorite
import com.example.fixphone.database.PhoneDatabase
import kotlinx.coroutines.launch

class FavoriteViewModel(val context: Context): ViewModel() {
    private val _allFavorite: MutableLiveData<List<Favorite>> = MutableLiveData()
    val allFavorite : LiveData<List<Favorite>> = _allFavorite

    private val myDb = PhoneDatabase.getInstance(context)
    fun getAllFavorite(){
        viewModelScope.launch {  }
    }

}