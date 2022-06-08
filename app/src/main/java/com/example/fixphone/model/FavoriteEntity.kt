package com.example.fixphone.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteEntity (
        @PrimaryKey(autoGenerate = true)
        var id_favorite: Int?,
        var id_user: Int,
        var phoneName: Int,
        var Image: String,
        var thumbnail : String

):Parcelable