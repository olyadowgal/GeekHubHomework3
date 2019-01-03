package com.example.olya.homework3.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.olya.homework3.entities.Message

@Dao
interface UserDao{
    @Query("SELECT * from users")
    fun getAllUsers(): LiveData<ArrayList<Message>>

    @Insert
    fun insert(word: Message)

    @Query("DELETE FROM users")
    fun deleteAll()
}