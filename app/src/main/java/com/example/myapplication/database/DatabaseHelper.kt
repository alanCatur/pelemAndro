
// DatabaseHelper.kt
package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.Movie

@Database(entities = [User::class, Movie::class], version = 2)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun wishlistDao(): WishlistDao
}

