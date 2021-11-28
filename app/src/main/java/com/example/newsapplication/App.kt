package com.example.newsapplication

import android.app.Application
import com.example.newsapplication.di.components.AppComponent
import com.example.newsapplication.di.components.DaggerAppComponent
import com.example.newsapplication.di.module.DatabaseModule
import com.example.newsapplication.utils.MySharedPreference
import com.example.newsapplication.utils.ThemeHelper
import com.yariksoffice.lingver.Lingver

class App:Application() {
    lateinit var mySharedPreference: MySharedPreference
    override fun onCreate() {
        super.onCreate()
        appComponent=DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(applicationContext)).build()
        Lingver.init(this, "uz")
        mySharedPreference = MySharedPreference.getinstance(this)

        if (mySharedPreference.getsharedpreference("isDark") == "1") {
            ThemeHelper.applyTheme(ThemeHelper.darkMode)

        } else {
            ThemeHelper.applyTheme(ThemeHelper.lightMode)

        }

    }
    companion object{
        lateinit var appComponent: AppComponent
    }
}