package com.example.fixphone.model

import com.google.gson.annotations.SerializedName

data class GetPhoneSpecsResponse(
    @SerializedName("data")
    val data: SpecsData,
    @SerializedName("status")
    val status: Boolean
)
data class SpecsData(
    @SerializedName("brand")
    val brand: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("os")
    val os: String,
    @SerializedName("phone_images")
    val phoneImages: List<String>,
    @SerializedName("phone_name")
    val phoneName: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("specifications")
    val specifications: List<Specification>,
    @SerializedName("storage")
    val storage: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)
data class Specification(
    @SerializedName("specs")
    val specs: List<Spec>,
    @SerializedName("title")
    val title: String
)
data class Spec(
    @SerializedName("key")
    val key: String,
    @SerializedName("val")
    val valX: List<String>
)