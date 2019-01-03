package com.example.olya.homework3.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message (
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    val messageUser: Int,
    var messageText: String
)