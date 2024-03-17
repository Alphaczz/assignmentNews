package com.example.assignmentornet.domain.repository

import com.example.assignmentornet.data.model.NewsModel


interface newsRepo
{
    suspend fun savePost(postModel: NewsModel)
    suspend fun getPost():List<NewsModel>?
    suspend fun  updatePost(postModel: NewsModel)
    suspend fun deletePost(newsId:Long)
    suspend fun getOneFromDb(newsId:Long):NewsModel?
    suspend fun incrementLike(postModel: NewsModel)
    suspend fun incrementDislike( postModel: NewsModel)

}