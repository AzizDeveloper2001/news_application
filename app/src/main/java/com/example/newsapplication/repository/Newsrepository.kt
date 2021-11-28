package com.example.newsapplication.repository

import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.database.dao.CategoryDao
import com.example.newsapplication.database.dao.NewsDao
import com.example.newsapplication.database.dao.SavedArticlesDao
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.networking.ApiService
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Newsrepository @Inject constructor(val apiService: ApiService,
                                         val newsDao: NewsDao,
                                         val categoryDao: CategoryDao,
                                         val savedArticlesDao: SavedArticlesDao) {

    suspend fun getallNews(category:String)= flow {emit(apiService.gettopnews(category=category))  }

    suspend fun addCategoryall(categoryModel: List<CategoryModel>)=categoryDao.addAllCategories(categoryModel)

    suspend fun editCategory(categoryModel: CategoryModel)=categoryDao.editCategory(categoryModel)

    suspend fun getAllCategories()= flow {emit(categoryDao.allCategory())  }

    suspend fun getAllNewsFromDb()= flow {emit(newsDao.allnews())  }

    suspend fun addAllNews(list: List<ArticlesItem>)= newsDao.addAll(list)

    suspend fun deleteAllNews()=newsDao.deleteall()

    suspend fun addSavedArticles(articlesItem: BookMarkEntity)=savedArticlesDao.addArticle(articlesItem)

    suspend fun deleteArticles(articlesItem: BookMarkEntity)=savedArticlesDao.deleteArticle(articlesItem)

    suspend fun getallSavedArticles()= flow {emit(savedArticlesDao.allArticle())  }

    suspend fun searchNews(q:String)= flow {emit(apiService.getcategorynews(q=q)) }
}