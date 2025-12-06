package com.example.Project06

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Defines the db and tells Room which entry's the db will hold
@Database(entities = [FoodEntity::class], version = 1)

// give access to the dao
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {

        // holds the single instance of the shared database
        @Volatile
        private var database: AppDatabase? = null

        // returns database
        fun getDb(context: Context): AppDatabase =
            database ?: synchronized(this) {
                database ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "foods-db"
                ).build().also { database = it }
            }
    }
}
