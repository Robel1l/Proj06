package com.example.Project06

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    // return all food entries
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    // add new food
    @Insert
    suspend fun insert(food: FoodEntity)

    // delete all food
    @Query("DELETE FROM food_table")
    suspend fun deleteAll()
}
