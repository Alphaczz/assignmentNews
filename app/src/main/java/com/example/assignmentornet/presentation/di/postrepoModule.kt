package com.example.assignmentornet.presentation.di

import com.example.assignmentornet.data.dataSource.localDataSource
import com.example.assignmentornet.domain.repository.newsRepo
import com.example.assignmentornet.domain.repository.newsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PostRepoModule {

    @Provides
    fun provideNewsRepo(localDataSource: localDataSource): newsRepo {
        return newsRepoImpl(localDataSource)
    }
}