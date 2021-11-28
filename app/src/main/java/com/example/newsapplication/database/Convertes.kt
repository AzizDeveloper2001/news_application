package com.example.newsapplication.database

import androidx.room.TypeConverter
import com.example.newsapplication.models.Source
import com.google.gson.Gson


class Convertes {
    @TypeConverter
    fun fromSource(source: Source) :String{
        return Gson().toJson(source)
    }
    @TypeConverter
    fun  tvSource(name: String): Source {
        return Gson().fromJson(name,Source::class.java)
    }
}