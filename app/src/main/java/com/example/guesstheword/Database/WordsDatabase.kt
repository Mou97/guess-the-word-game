package com.example.guesstheword.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.guesstheword.DAO.WordDAO
import com.example.guesstheword.Entity.Word
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


@Database(entities = [ Word::class], version = 1)
abstract class WordsDatabase : RoomDatabase(){
    abstract  fun wordDao(): WordDAO

    companion object {
        private var INSTANCE : WordsDatabase? = null

        fun getInstance(context: Context): WordsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                    WordsDatabase::class.java, "Sample.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        val initWords : List<Word> = listOf(
                            Word(null,"STAR"), Word(null,"BOOK"),
                            Word(null,"FOOD"), Word(null,"HOME"),
                            Word(null,"CAKE"), Word(null,"FAKE"),
                            Word(null,"LUCK"), Word(null,"DOLE")
                        )
                        ioThread {
                            getInstance(context).wordDao().insertAll(initWords)
                        }
                    }
                })
                .build()


        fun destroyInstance(){
            INSTANCE = null
        }


    }
}