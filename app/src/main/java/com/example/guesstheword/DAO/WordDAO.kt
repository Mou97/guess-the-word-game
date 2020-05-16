package com.example.guesstheword.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.guesstheword.Entity.Word

@Dao
interface WordDAO {
    @Query("select * from words")
    fun getAll(): List<Word>

    @Query("SELECT * from words where id = (:id)")
    fun findById(id: Long): Word

    @Insert
    fun insertAll(vararg words: Word)
}