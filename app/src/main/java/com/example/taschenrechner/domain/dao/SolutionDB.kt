package com.example.taschenrechner.domain.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taschenrechner.domain.entity.HistoryItem

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
abstract class CalculatorDatabase : RoomDatabase() {
    abstract fun solutionDao(): SolutionDao
}