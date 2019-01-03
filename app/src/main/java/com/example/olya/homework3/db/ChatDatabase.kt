package com.example.olya.homework3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.olya.homework3.db.daos.MessageDao
import com.example.olya.homework3.db.daos.UserDao
import com.example.olya.homework3.entities.Message
import com.example.olya.homework3.entities.User

@Database(entities = [User::class, Message::class], version = 1)
public abstract class ChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var instance: ChatDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ChatDatabase::class.java, "chatter.db"
            )
                .build()
    }
}