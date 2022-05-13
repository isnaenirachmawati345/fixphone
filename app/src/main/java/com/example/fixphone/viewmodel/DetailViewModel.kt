package com.example.fixphone.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixphone.model.GetPhoneSpecsResponse
import com.example.fixphone.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val phoneSpecs: MutableLiveData<GetPhoneSpecsResponse> = MutableLiveData()


    fun getPhoneSpec(id: String) {
        ApiClient.instance.getSpecsPhoneById(id).enqueue(object  : Callback<GetPhoneSpecsResponse> {
            override fun onResponse(
                call: Call<GetPhoneSpecsResponse>,
                response: Response<GetPhoneSpecsResponse>
            ){
                phoneSpecs.postValue(response.body())

            }

            override fun onFailure(call: Call<GetPhoneSpecsResponse>, t: Throwable) {
                Log.d("DetailViewModel", "${t.message}")
            }
        }
        )
    }
}