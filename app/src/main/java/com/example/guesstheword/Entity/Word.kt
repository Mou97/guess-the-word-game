package com.example.guesstheword.Entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "word") val word: String

) : Parcelable