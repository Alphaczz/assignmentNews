package com.example.assignmentornet.presentation.di

import com.example.assignmentornet.domain.repository.newsRepo
import com.example.assignmentornet.presentation.viewmodel.CreatePostViewModel
import com.example.assignmentornet.presentation.viewmodel.CreatePostViewModelFactory
import com.example.assignmentornet.presentation.viewmodel.PostViewModel
import com.example.assignmentornet.presentation.viewmodel.PostViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelModule {

    @Provides
    fun providePostViewModel(newsRepo: newsRepo): PostViewModel {
        return PostViewModel(newsRepo)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(
        newsRepo: newsRepo
    ): PostViewModelFactory {
        return PostViewModelFactory(
            newsRepo
        )
    }


}
@Module
@InstallIn(ViewModelComponent::class)
object CreatePostViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideCreatePostViewModel(repository:newsRepo): CreatePostViewModel {
        return CreatePostViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideCreatePostViewModelFactory(
        newsRepo: newsRepo
    ): CreatePostViewModelFactory {
        return CreatePostViewModelFactory(
            newsRepo
        )
    }
}

