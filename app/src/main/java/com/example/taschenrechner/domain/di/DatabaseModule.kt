package com.example.taschenrechner.domain.di

import android.content.Context
import androidx.room.Room
import com.example.taschenrechner.domain.dao.CalculatorDatabase
import com.example.taschenrechner.domain.dao.SolutionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): CalculatorDatabase {
        return Room.databaseBuilder(
            appContext,
            CalculatorDatabase::class.java,
            "calculator_db"
        ).build()
    }

    @Provides
    fun provideSolutionDao(database: CalculatorDatabase): SolutionDao {
        return database.solutionDao()
    }
}

