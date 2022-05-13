package com.example.fixphone.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixphone.model.User

class HomeViewModel: ViewModel() {
    var userData : MutableLiveData<User> = MutableLiveData()

    fun  getUserData(user: User){
        userData.postValue(user)
    }
}