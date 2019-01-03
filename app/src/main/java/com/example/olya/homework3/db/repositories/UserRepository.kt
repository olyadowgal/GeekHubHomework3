package com.example.olya.homework3.db.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.olya.homework3.db.ChatDatabase
import com.example.olya.homework3.db.daos.UserDao
import com.example.olya.homework3.entities.User

class UserRepository(db: ChatDatabase) : BaseRepository() {

    private val userDao: UserDao = db.userDao()
    internal val allUsers: LiveData<List<User>> = userDao.selectAllLiveData()


    @WorkerThread
    fun insert(user: User) = userDao.insert(user)

    fun insertAsync(user: User, callback: () -> Unit = {}) = executor.execute {
        insert(user)
        callback()
    }


}