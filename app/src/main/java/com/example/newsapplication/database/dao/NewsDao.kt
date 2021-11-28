package com.example.newsapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.newsapplication.models.ArticlesItem


@Dao
interface NewsDao {
        @Insert(onConflict = REPLACE)
    suspend fun addAll(articlelist: List<ArticlesItem>)

    @Query("select * from articlesitem")
   suspend fun allnews():List<ArticlesItem>

   @Query("delete from articlesitem")
   suspend fun deleteall()
}