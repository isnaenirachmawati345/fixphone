package com.example.fixphone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fixphone.model.User
import com.example.fixphone.model.FavoriteEntity
import com.example.fixphone.service.Repository
import kotlinx.coroutines.launch

class FavoriteFragmentViewModel(private val repository: Repository): ViewModel() {
    private val _dataFavorite = MutableLiveData<List<FavoriteEntity>>()
    val dataFavorite : LiveData<List<FavoriteEntity>> get() = _dataFavorite

    private val _userPref = MutableLiveData<User>()
    val userPref : LiveData<User> get() = _userPref

    fun getDataFavorite(id_user: Int){
        viewModelScope.launch {
            _dataFavorite.postValue(repository.getAllFavoriteById(id_user))
        }
    }

    fun getUserPref(){
        viewModelScope.launch {
            repository.getUserPref().collect{
                _userPref.postValue(it)
            }
        }
    }
}