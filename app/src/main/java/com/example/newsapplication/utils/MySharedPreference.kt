package com.example.newsapplication.utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPreference {
      companion object {
          private var instance: MySharedPreference? = null
          private var sharedPreference: SharedPreferences? = null
          private var editor: SharedPreferences.Editor? = null
          private const val FILE_NAME = "share_pref"

          fun getinstance(context: Context): MySharedPreference {
              if (instance == null) {
                  instance = MySharedPreference()
                  sharedPreference = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                  editor = sharedPreference?.edit()
              }
              return instance!!
          }
      }

    fun getsharedpreference(key:String):String{
      return  sharedPreference?.getString(key, "")!!
    }
    fun putsharedprefence(key:String,code:String){
        editor?.putString(key,code)?.commit()
    }
}