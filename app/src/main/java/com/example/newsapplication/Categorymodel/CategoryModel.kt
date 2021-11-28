package com.example.newsapplication.Categorymodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryModel(@PrimaryKey var name:String,var image:Int,var saved:Boolean) {
}