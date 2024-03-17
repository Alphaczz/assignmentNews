package com.example.assignmentornet.domain.util

import com.example.assignmentornet.data.model.NewsModel
import java.util.Date
import java.util.UUID


    fun generateUniqueId(): Long {
        val uuid = UUID.randomUUID()
        return uuid.leastSignificantBits
    }

// Function to create a NewsModel instance
 fun createPost(
    newsTitle: String,
    newsBanner: String?,
    newsDes: String?,
    author: String,
    newsCategory: String?,
    newsStatus:Int,
    newsRegion:String?

): NewsModel {
    return NewsModel(
        newsId = generateUniqueId(),
        newsTitle = newsTitle,
        newsDesc = newsDes,
        createdBy = author,
        newsBanner = newsBanner,
        createdOn = Date().toString(),
        UpdatedBy = null,
        category = newsCategory,
        city = null,
        country = null,
        dislike = 0,
        language = "English",
        likes = 0,
        region = newsRegion,
        status = newsStatus,
        updatedOn = null
    )
}
