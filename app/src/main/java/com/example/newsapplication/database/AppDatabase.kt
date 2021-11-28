package com.example.newsapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.database.dao.CategoryDao
import com.example.newsapplication.database.dao.NewsDao
import com.example.newsapplication.database.dao.SavedArticlesDao
import com.example.newsapplication.models.ArticlesItem

@Database(entities = [ArticlesItem::class,CategoryModel::class,BookMarkEntity::class],version = 1)
@TypeConverters(Convertes::class)
abstract class AppDatabase:RoomDatabase(){
    abstract fun newsdao():NewsDao
    abstract fun categorydao():CategoryDao
    abstract fun savedarticles():SavedArticlesDao
}