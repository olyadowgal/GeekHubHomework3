package com.example.olya.homework3.db.repositories

import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class BaseRepository {

    protected val executor: Executor = Executors.newCachedThreadPool()
}