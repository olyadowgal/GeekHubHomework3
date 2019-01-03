package com.example.olya.homework3.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class User (
    @PrimaryKey(autoGenerate = true)
    private val id: Int,
    var userName: String
)