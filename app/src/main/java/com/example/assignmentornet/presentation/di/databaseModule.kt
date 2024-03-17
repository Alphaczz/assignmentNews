package com.example.assignmentornet.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.assignmentornet.data.db.NewsDao
import com.example.assignmentornet.data.db.NewsDbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class databaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): NewsDbDatabase {
        return Room.databaseBuilder(context,
            NewsDbDatabase::class.java,"postdb")
            .build()
    }
    @Singleton
    @Provides
    fun provideNewsDao(postDatabase: NewsDbDatabase):NewsDao{
        return  postDatabase.NewsDao()
    }
}