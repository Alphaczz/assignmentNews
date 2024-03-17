package com.example.assignmentornet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentornet.domain.repository.newsRepo


class PostViewModelFactory(
    private val postRepo: newsRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(postRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}