package com.example.assignmentornet.presentation.di

import com.example.assignmentornet.data.dataSource.localDataSource
import com.example.assignmentornet.data.db.NewsDao
import com.example.assignmentornet.dataSourceImpl.localDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class localDataModule()
{
    @Singleton
    @Provides
    fun provideLocalModule(dao: NewsDao): localDataSource
    {
        return localDataSourceImp(dao)
    }
}