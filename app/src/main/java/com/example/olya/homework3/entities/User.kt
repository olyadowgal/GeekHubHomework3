package com.example.olya.homework3.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey
    val id: Int,
    val userName: String
)