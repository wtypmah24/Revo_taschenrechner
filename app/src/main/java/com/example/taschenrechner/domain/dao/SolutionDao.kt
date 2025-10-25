package com.example.taschenrechner.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taschenrechner.domain.entity.HistoryItem

@Dao
interface SolutionDao {

    @Insert
    suspend fun insert(item: HistoryItem)

    @Query("SELECT * FROM calculator_history ORDER BY id DESC")
    suspend fun getAll(): List<HistoryItem>

    @Query("DELETE FROM calculator_history")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteItem(item: HistoryItem)
}