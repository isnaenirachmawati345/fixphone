package com.example.fixphone.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Favorite (
     @PrimaryKey(autoGenerate = true) var id_user : Int?,
     val name: String,
     val image : String,
     val phone_name : String
        )