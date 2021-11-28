package com.example.newsapplication.utils

import com.example.newsapplication.models.ArticlesItem

sealed class NewsResource {
    object Loadding:NewsResource()
    class Success(val articlelist: List<ArticlesItem?>?):NewsResource()
    class Error(val message:String):NewsResource()
}