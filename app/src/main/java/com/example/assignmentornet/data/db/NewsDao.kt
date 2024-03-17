package com.example.assignmentornet.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.assignmentornet.data.model.NewsModel

@Dao
interface NewsDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun savePost(post: NewsModel)

    @Query("SELECT * FROM news_model")
    suspend fun getAllPost(): List<NewsModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun updatePost(news: NewsModel)

    @Query("DELETE FROM news_model WHERE newsId = :newsId")
  suspend  fun deletePostById(newsId: Long)

    @Query("UPDATE news_model SET likes = likes + 1 WHERE newsId = :entityId")
   suspend fun incrementLikes(entityId: Long)

    @Query("UPDATE news_model SET dislike = dislike + 1 WHERE newsId = :entityId")
   suspend fun incrementDislikes(entityId: Long)

    @Query("SELECT * FROM news_model WHERE newsId = :newsId")
   suspend  fun getPostById(newsId: Long): NewsModel?

}