package com.example.fixphone.viewmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id_favorite: Int?,
    var id_user: Int,
    var phoneName : Int,
    var image : String,
    var thumbnail : String,
    var detail :String,
    var slug : String
): Parcelable