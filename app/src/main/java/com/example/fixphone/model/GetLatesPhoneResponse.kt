package com.example.fixphone.model

import com.google.gson.annotations.SerializedName

data class GetLatestPhoneResponse(
    @SerializedName("data")
    val data: PhoneData,
    @SerializedName("status")
    val status: Boolean
)
data class PhoneData(
    @SerializedName("phones")
    val phones: List <Phone>,
    @SerializedName("title")
    val title: String
)
data class Phone(
    @SerializedName("detail")
    val detail: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("phone_name")
    val phoneName: String,
    @SerializedName("slug")
    val slug: String
)