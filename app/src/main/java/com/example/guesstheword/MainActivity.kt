package com.example.guesstheword

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.guesstheword.Database.WordsDatabase
import com.example.guesstheword.Entity.Word
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val words : ArrayList<String> = ArrayList()
    private var database: WordsDatabase? = null
    private var currentWord: String = "????"

    private var step : Int = 0
    private val sharedPrefFile = "progress"
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences= this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        step = sharedPreferences.getInt("step",0)


        Observable.fromCallable {
                // init database
                database = WordsDatabase.getInstance(context = this)

                database?.wordDao()?.getAll()

            }.doOnNext {
                words.clear()
                it?.map {
                    words.add(it.word)
                }
            }.doOnComplete {
                currentWord = words.get(step)
                runOnUiThread(Runnable {
                    run {
                        score.text= "You got $step out of " + words.size
                        updateWord()
                    }
                })


            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()


        go.setOnClickListener {
            if(firstX.text.trim().isEmpty() || secondX.text.trim().isEmpty()){
                return@setOnClickListener Toast.makeText(applicationContext, "Fill the inputs ", Toast.LENGTH_SHORT).show()
            }

            if(firstX.text[0].toUpperCase() == currentWord[1] && secondX.text[0].toUpperCase() == currentWord[2] ){
                Toast.makeText(applicationContext, "Correct ", Toast.LENGTH_SHORT).show()
                correctAnswer()
            }else {
                Toast.makeText(applicationContext , "False" , Toast.LENGTH_SHORT).show()
            }

            firstX.text.clear()
            secondX.text.clear()
        }
    }

    fun correctAnswer(){
        step++
        score.text= "You got $step out of " + words.size
        currentWord = words.get(step)
        updateWord()
        saveState()
    }

    fun saveState(){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("step", step)
        with(editor){
            this.apply()
            this.commit()
        }
    }

    fun updateWord(){
        firstletter.text= currentWord[0].toString()
        lastLetter.text = currentWord[3].toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        WordsDatabase.destroyInstance()
    }
}
