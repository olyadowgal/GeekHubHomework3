package com.example.olya.homework3.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.olya.homework3.db.daos.MessageDao
import com.example.olya.homework3.db.daos.UserDao
import com.example.olya.homework3.entities.Message
import com.example.olya.homework3.entities.User

@Database(
    entities = [User::class, Message::class],
    version = 1
)
abstract class ChatDatabase : RoomDatabase() {

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
                ChatDatabase::class.java, "chat.db"
        )
                .addCallback(chatCallback)
                .build()

        private val chatCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                ChatDatabase.PopulateDbAsyncTask(ChatDatabase.instance!!).execute()
            }
        }
    }

    private class PopulateDbAsyncTask internal constructor(db: ChatDatabase) : AsyncTask<Void, Void, Void>() {

        private val userDao: UserDao = db.userDao()

        override fun doInBackground(vararg voids: Void): Void? {
            userDao.insert(User(1, "1"))
            userDao.insert(User(2, "2"))
            return null
        }
    }

}