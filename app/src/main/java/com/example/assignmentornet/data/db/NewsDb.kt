package com.example.assignmentornet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignmentornet.data.model.NewsModel

@Database(entities = [NewsModel::class], version = 1, exportSchema = false)
abstract class NewsDbDatabase : RoomDatabase()
{
    abstract fun NewsDao(): NewsDao
}