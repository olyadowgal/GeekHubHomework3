package com.example.olya.homework3.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.olya.homework3.entities.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * from users ORDER BY id ASC")
    fun selectAllLiveData(): LiveData<List<User>>

    @Query("SELECT * from users ORDER BY id ASC")
    fun selectAll(): List<User>
}
