package com.example.assignmentornet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "News_Model")
data class NewsModel(
    @PrimaryKey
    val newsId:Long,
    val newsTitle:String? ,
    val newsDesc:String?,
    val newsBanner:String?,
    val category:String?,
    val region:String?,
    val status:Int,
    val language: String?,
    val city:String?,
    val likes:Int?,
    val dislike:Int?,
    val country:String?,
    val createdOn:String?,
    val createdBy:String?,
    val updatedOn:String?,
    val UpdatedBy:String?
)
