package com.example.olya.homework3.applicaitons

import android.app.Application
import com.example.olya.homework3.db.ChatDatabase
import com.example.olya.homework3.db.repositories.MessageRepository
import com.example.olya.homework3.db.repositories.UserRepository

class AppClass: Application() {

    companion object {

        lateinit var application: Application

        val db by lazy { ChatDatabase.invoke(application) }

        val messageRepository by lazy { MessageRepository(db) }

        val userRepository by lazy { UserRepository(db) }
    }

    init {
        application = this
    }
}