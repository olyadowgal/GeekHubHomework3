package com.example.olya.homework3.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.olya.homework3.entities.Message

@Dao
interface MessageDao {

    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("SELECT * from messages ORDER BY id ASC")
    fun selectAllLiveData(): LiveData<List<Message>>

    @Query("SELECT * from messages ORDER BY id ASC")
    fun selectAll(): List<Message>
}