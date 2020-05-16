package com.example.guesstheword.DAO

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.guesstheword.Entity.User

interface UserDAO {
    @Query("select * from user where username=:username limit 1")
    fun findByUsername( username: String) : User

    @Insert
    fun insert(user: User)

    @Update(entity = User::class)
    fun update(user: User)
}