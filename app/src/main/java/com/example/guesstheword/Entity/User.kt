package com.example.guesstheword.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "progress") val progress : Int
)