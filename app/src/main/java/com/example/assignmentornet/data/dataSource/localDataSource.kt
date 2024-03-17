package com.example.assignmentornet.data.dataSource

import com.example.assignmentornet.data.model.NewsModel

interface localDataSource {
    suspend fun getFromDb():List<NewsModel>

    suspend fun updatePost(post: NewsModel)
    suspend fun saveToDb(post: NewsModel)
    suspend fun deletePost(postid: Long)
    suspend fun getOneFromDb(postid:Long): NewsModel
    suspend fun incrementLike(post: NewsModel)
    suspend fun incrementDislike(post: NewsModel)
}