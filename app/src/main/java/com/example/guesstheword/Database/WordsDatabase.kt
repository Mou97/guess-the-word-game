package com.example.guesstheword.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guesstheword.DAO.UserDAO
import com.example.guesstheword.Entity.User
import com.example.guesstheword.Entity.Word


@Database(entities = [User::class ,  Word::class], version = 1 )
abstract class WordsDatabase : RoomDatabase(){
    abstract  fun  userDao(): UserDAO

    companion object {
        private var INSTANCE : WordsDatabase? = null

        fun getInstance(context: Context): WordsDatabase? {
            if (INSTANCE == null) {
                synchronized(WordsDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    WordsDatabase::class.java, "wordsgame.db" ).build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}