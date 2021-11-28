package com.example.newsapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class NewsData(
	val totalResults: Int? = null,
	val articles: List<ArticlesItem?>? = null,
	val status: String? = null
)

data class Source(
	val name: String? = null,
	val id: String? = null
)

@Entity
data class ArticlesItem(
	val publishedAt: String? = null,
	val author: String? = null,
	val urlToImage: String? = null,
	val description: String? = null,
	val source: Source? = null,
	val title: String? = null,
	@PrimaryKey
	val url: String,
	val content: String? = null
) : Serializable

