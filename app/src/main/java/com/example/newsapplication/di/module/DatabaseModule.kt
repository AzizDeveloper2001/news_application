package com.example.newsapplication.di.module

import android.content.Context
import androidx.room.Room
import com.example.newsapplication.database.AppDatabase
import com.example.newsapplication.database.dao.CategoryDao
import com.example.newsapplication.database.dao.NewsDao
import com.example.newsapplication.database.dao.SavedArticlesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext():Context=context

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context):AppDatabase{
       return Room.databaseBuilder(context,AppDatabase::class.java,"my_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(appDatabase: AppDatabase):NewsDao=appDatabase.newsdao()

    @Singleton
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase):CategoryDao=appDatabase.categorydao()

    @Singleton
    @Provides
    fun provideSavedDao(appDatabase: AppDatabase):SavedArticlesDao=appDatabase.savedarticles()
}