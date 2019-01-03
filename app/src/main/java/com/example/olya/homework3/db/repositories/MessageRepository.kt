package com.example.olya.homework3.db.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.olya.homework3.db.ChatDatabase
import com.example.olya.homework3.db.daos.MessageDao
import com.example.olya.homework3.entities.Message

class MessageRepository(db: ChatDatabase) : BaseRepository() {

    private val messageDao: MessageDao = db.messageDao()

    val allMessages: LiveData<List<Message>> = messageDao.selectAllLiveData()

    @WorkerThread
    fun selectAll() = messageDao.selectAll()

    fun selectAllAsync(callback: (List<Message>) -> Unit) = executor.execute {
        callback(messageDao.selectAll())
    }

    @WorkerThread
    fun insert(message: Message) = messageDao.insert(message)

    fun insertAsync(message: Message, callback: () -> Unit = {}) = executor.execute {
        insert(message)
        callback()
    }

    @WorkerThread
    fun update(message: Message) = messageDao.update(message)

    fun updateAsync(message: Message, callback: () -> Unit = {}) = executor.execute {
        update(message)
        callback()
    }

    @WorkerThread
    fun delete(message: Message) = messageDao.delete(message)

    fun deleteAsync(message: Message, callback: () -> Unit = {}) = executor.execute {
        delete(message)
        callback()
    }
}