package com.example.olya.homework3.db.repositories

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class BaseRepository {

    protected val asyncExecutor: Executor = Executors.newCachedThreadPool()
    protected val mainThreadExecutor = object: Executor {

        val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post { command.run() }
        }
    }
}