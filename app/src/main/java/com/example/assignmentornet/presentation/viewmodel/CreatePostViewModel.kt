package com.example.assignmentornet.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.assignmentornet.data.model.NewsModel
import com.example.assignmentornet.domain.repository.newsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(private val repo: newsRepo) : ViewModel() {


    suspend fun saveNews(post: NewsModel)
    {
        viewModelScope.launch {
            repo.savePost(post)
            Log.i("TAGGY","POST SAVED")
        }
    }

}
