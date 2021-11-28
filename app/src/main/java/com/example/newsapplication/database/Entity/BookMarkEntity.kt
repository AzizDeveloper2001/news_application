package com.example.newsapplication.database.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapplication.models.Source

@Entity
data class BookMarkEntity(
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: Source? = null,
    val title: String? = null,
    @PrimaryKey
    val url: String,
    val content: String? = null
)
