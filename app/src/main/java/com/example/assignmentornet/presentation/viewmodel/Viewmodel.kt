package com.example.assignmentornet.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentornet.data.model.NewsModel
import com.example.assignmentornet.domain.repository.newsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repo: newsRepo) : ViewModel() {

    private val _postLiveData = MutableLiveData<List<NewsModel>>()
    val postLiveData: LiveData<List<NewsModel>> get() = _postLiveData
    var post: NewsModel?=null
    init {
        getPost()
    }
    fun savePost(post: NewsModel)
    {
        viewModelScope.launch {
            repo.savePost(post)
            Log.i("TAGGY","POST SAVED")
        }
    }
    private suspend fun refreshPostList() {
        val updatedList = repo.getPost()
        _postLiveData.postValue(updatedList!!)
    }
    fun getOneFromDb(postid: Long): LiveData<NewsModel> {
        val liveData = MutableLiveData<NewsModel>()

        viewModelScope.launch {
            try {
                val post = repo.getOneFromDb(postid)
                post?.let {
                    liveData.postValue(it)
                    Log.i("TAGGY", "POST retrieved ${it.newsTitle}")
                }
            } catch (e: Exception) {
                Log.i("TAGGY", "Error retrieving post: ${e.message}")
            }
        }

        return liveData
    }
    fun incrementLikes(post: NewsModel) {
        viewModelScope.launch {
            try {
                repo.incrementLike(post)
                val updatedList = repo.getPost()
                _postLiveData.postValue(updatedList!!)
                refreshPostList()
            } catch (e: Exception) {
                Log.i("incrementLikes",e.message.toString())
            }
        }
    }

    fun incrementDislikes(post: NewsModel) {
        viewModelScope.launch {
            try {
                repo.incrementDislike(post)
                val updatedList = repo.getPost()
                _postLiveData.postValue(updatedList!!)
                refreshPostList()
            } catch (e: Exception) {
                Log.i("getMovies",e.message.toString())
            }
        }
    }

    fun getPost() {
        viewModelScope.launch {
            try {
                val postList = repo.getPost()
                _postLiveData.postValue(postList!!)
                Log.i("getPosts", _postLiveData.value?.get(0)?.region.toString())

            } catch (e: Exception) {
                Log.i("getPosts",e.message.toString())
            }
        }
    }

    fun updatePost(post: NewsModel) {
        viewModelScope.launch {
            try {
                repo.updatePost(post)
                // Optionally, fetch and emit the updated list if needed
                val updatedList = repo.getPost()
                _postLiveData.postValue(updatedList!!)
            } catch (e: Exception) {
                Log.i("updatePost",e.message.toString())
            }
        }
    }



    fun deletePost(postid: Long) {
        viewModelScope.launch {
            try {
                repo.deletePost(postid)
                val updatedList = repo.getPost()
                _postLiveData.postValue(updatedList!!)
                refreshPostList()
            } catch (e: Exception) {
                Log.i("deletePOSTERROR",e.message.toString())
            }
        }
    }
}
