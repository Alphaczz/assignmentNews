package com.example.assignmentornet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentornet.domain.repository.newsRepo

class CreatePostViewModelFactory (
    private val postRepo: newsRepo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatePostViewModel::class.java)) {
            return CreatePostViewModel(postRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
