package com.example.assignmentornet.dataSourceImpl

import android.util.Log
import com.example.assignmentornet.data.dataSource.localDataSource
import com.example.assignmentornet.data.db.NewsDao
import com.example.assignmentornet.data.model.NewsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class localDataSourceImp(private val newsDao: NewsDao): localDataSource
{
    override suspend fun getFromDb(): List<NewsModel> {
      return newsDao.getAllPost()
    }

    override suspend fun updatePost(post: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.updatePost(post)
        }
    }

    override suspend fun saveToDb(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.savePost(news)
            Log.i("TAGY","postid: ${news.newsId}")
        }
    }

    override suspend fun deletePost(postid: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.deletePostById(postid)
        }
    }


    override suspend fun incrementLike(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.incrementLikes(news.newsId)
        }
    }

    override suspend fun incrementDislike(news: NewsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            newsDao.incrementDislikes(news.newsId)
        }
    }


    override suspend fun getOneFromDb(postid: Long):NewsModel {
        return newsDao.getPostById(postid)!!

    }


}
