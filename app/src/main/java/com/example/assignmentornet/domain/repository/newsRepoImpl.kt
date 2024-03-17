package com.example.assignmentornet.domain.repository

import android.util.Log
import com.example.assignmentornet.data.dataSource.localDataSource
import com.example.assignmentornet.data.model.NewsModel
import javax.inject.Inject

class newsRepoImpl @Inject constructor(
   private  val localDataSource: localDataSource
): newsRepo {
    override suspend fun savePost(newsModel: NewsModel) {
        localDataSource.saveToDb(newsModel)
    }

    override suspend fun getPost(): List<NewsModel>? {
       return  getPostFromRoom()
    }

    override suspend fun updatePost(newsModel: NewsModel) {
        localDataSource.updatePost(newsModel)
    }

    override suspend fun incrementLike(postModel: NewsModel) {
        localDataSource.incrementLike(postModel)
    }

    override suspend fun incrementDislike(postModel: NewsModel) {
        localDataSource.incrementDislike(postModel)
    }

    override suspend fun deletePost(postid: Long) {
        localDataSource.deletePost(postid)
    }

    override suspend fun getOneFromDb(postid: Long):NewsModel {
        return localDataSource.getOneFromDb(postid)
    }

    suspend fun getPostFromRoom():List<NewsModel>
    {
        lateinit var postList: List<NewsModel>
        try {
            postList= localDataSource.getFromDb()
        }
        catch (exeception:java.lang.Exception)
        {
            Log.i("GetPostFromRoom",exeception.message.toString())
        }
        return postList
    }

}