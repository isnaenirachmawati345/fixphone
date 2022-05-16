package com.example.fixphone.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
 data class User(
    @PrimaryKey(autoGenerate = true)
    var id_user: Int?,
    @ColumnInfo(name = "nama")
    var nama: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "image")
    var image: String
):Parcelable
