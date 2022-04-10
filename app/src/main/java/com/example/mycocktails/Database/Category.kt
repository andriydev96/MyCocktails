package com.example.mycocktails.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
        @PrimaryKey
        val name: String
) {
    override fun toString(): String {
        return name
    }
}