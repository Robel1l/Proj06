package com.example.Project06

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Long =0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "cals") val calories: Long?
)