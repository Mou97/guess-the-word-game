package com.example.guesstheword.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.guesstheword.Entity.Word
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

@Dao
interface WordDAO {
    @Query("select * from words")
    fun getAll():List<Word>

    @Query("SELECT * from words where id = (:id)")
    fun findById(id: Long): Word

    @Insert(onConflict = REPLACE)
    fun insert(word: Word)

    @Query("delete from words")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    @JvmSuppressWildcards
    fun insertAll(words: List<Word>)

}