package com.example.newsapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.repository.Newsrepository
import com.example.newsapplication.utils.NetworkHelper
import com.example.newsapplication.utils.NewsResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModels @Inject constructor(val networkHelper: NetworkHelper,
                                         val newsrepository: Newsrepository):ViewModel() {

    fun getNewsfromNet(category:String):StateFlow<NewsResource>{
        val stateFlow= MutableStateFlow<NewsResource>(NewsResource.Loadding)
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                newsrepository.getallNews(category).catch {
                    stateFlow.emit(NewsResource.Error(it.message!!))
                }.collect {
                    if(it.isSuccessful){
                        newsrepository.deleteAllNews()
                        newsrepository.addAllNews(it.body()?.articles as List<ArticlesItem>)

                        stateFlow.emit(NewsResource.Success(it.body()!!.articles))
                    } else {
                        stateFlow.emit(NewsResource.Error(it.message()))
                    }

                }
            } else {
                viewModelScope.launch {
                    newsrepository.getAllNewsFromDb().collect {
                        if(it.isNotEmpty()){
                            stateFlow.emit(NewsResource.Success(it))
                        } else {
                            stateFlow.emit(NewsResource.Error("No internet connection"))
                        }
                    }
                }

            }
        }

        return stateFlow
    }


    //category functions
    fun addCategoryall(categoryModel: List<CategoryModel>){
        viewModelScope.launch {
            newsrepository.addCategoryall(categoryModel)
        }
    }

    fun editCategory(categoryModel: CategoryModel){
        viewModelScope.launch {
            newsrepository.editCategory(categoryModel)
        }
    }
    fun getAllCategoies():StateFlow<List<CategoryModel>>{
        var stateflow= MutableStateFlow<List<CategoryModel>>(emptyList())
        viewModelScope.launch {
        newsrepository.getAllCategories().collect {
            stateflow.emit(it)
        }

        }
        return stateflow
    }

    fun addSavedArticles(articlesItem: BookMarkEntity){
        viewModelScope.launch {
            newsrepository.addSavedArticles(articlesItem)
        }
    }

    fun deleteSavedArticles(articlesItem: BookMarkEntity){
        viewModelScope.launch {
            newsrepository.deleteArticles(articlesItem)
        }
    }
    fun getAllSavedArticles():StateFlow<List<BookMarkEntity>>{
        var stateflow= MutableStateFlow<List<BookMarkEntity>>(emptyList())
        viewModelScope.launch {
            newsrepository.getallSavedArticles().collect {
                stateflow.emit(it)
            }

        }
        return stateflow
    }

    fun getsearchwords(q:String):StateFlow<NewsResource>{
        val stateFlow= MutableStateFlow<NewsResource>(NewsResource.Loadding)
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                newsrepository.searchNews(q).catch {
                    stateFlow.emit(NewsResource.Error(it.message!!))
                }.collect {
                    if(it.isSuccessful){
                        stateFlow.emit(NewsResource.Success(it.body()?.articles))
                    } else {
                        stateFlow.emit(NewsResource.Error(it.message()))
                    }

                }
            } else {
                stateFlow.emit(NewsResource.Error("No internet connection"))
            }
        }
        return stateFlow
    }







}