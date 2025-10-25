package com.example.taschenrechner.domain.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calculator_history")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val expression: String,
    val result: String
)