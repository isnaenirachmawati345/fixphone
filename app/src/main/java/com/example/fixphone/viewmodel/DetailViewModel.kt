package com.example.fixphone.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fixphone.model.GetPhoneSpecsResponse
import com.example.fixphone.model.User
import com.example.fixphone.model.FavoriteEntity
import com.example.fixphone.service.ApiClient
import com.example.fixphone.service.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragmentViewModel(private val repository: Repository): ViewModel() {

    private val _dataFavoriteByCountry = MutableLiveData<FavoriteEntity>()
    val dataFavoriteByCountry : LiveData<FavoriteEntity> get() = _dataFavoriteByCountry

    private val _userPref = MutableLiveData<User>()
    val userPref : LiveData<User> get() = _userPref

    fun insertFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            repository.insertFavorite(favoriteEntity)
        }
    }

    fun checkFavorite(id_user: Int, country_name: String){
        viewModelScope.launch {
            _dataFavoriteByCountry.postValue(repository.getFavorite(id_user, country_name))
        }
    }

    fun deleteFavorite(id_user: Int, country_name: String){
        viewModelScope.launch {
            repository.deleteFavorite(id_user, country_name)
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

//class DetailViewModel : ViewModel() {
//    val phoneSpecs: MutableLiveData<GetPhoneSpecsResponse> = MutableLiveData()
//
//
//    fun getPhoneSpec(id: String) {
//        ApiClient.instance.getSpecsPhoneById(id).enqueue(object  : Callback<GetPhoneSpecsResponse> {
//            override fun onResponse(
//                call: Call<GetPhoneSpecsResponse>,
//                response: Response<GetPhoneSpecsResponse>
//            ){
//                phoneSpecs.postValue(response.body())
//
//            }
//
//            override fun onFailure(call: Call<GetPhoneSpecsResponse>, t: Throwable) {
//                Log.d("DetailViewModel", "${t.message}")
//            }
//        }
//        )
//    }
//}