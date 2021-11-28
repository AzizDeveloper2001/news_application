package com.example.newsapplication.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.models.ArticlesItem


@Dao
interface SavedArticlesDao {
    @Insert(onConflict = REPLACE)
    suspend fun addArticle(article: BookMarkEntity)

    @Query("select * from bookmarkentity")
    suspend fun allArticle():List<BookMarkEntity>

    @Delete
     suspend fun deleteArticle(article: BookMarkEntity)
}