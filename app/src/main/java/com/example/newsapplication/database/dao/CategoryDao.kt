package com.example.newsapplication.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.models.ArticlesItem



@Dao
interface CategoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun addCategory(article: CategoryModel)

    @Insert(onConflict = REPLACE)
    suspend fun addAllCategories(article: List<CategoryModel>)

    @Query("select * from categorymodel")
   suspend fun allCategory():List<CategoryModel>



   @Update
   suspend fun editCategory(categoryModel: CategoryModel)
}