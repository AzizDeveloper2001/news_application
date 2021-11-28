package com.example.newsapplication.di.components

import com.example.newsapplication.databinding.FragmentFourthBinding
import com.example.newsapplication.di.module.DatabaseModule
import com.example.newsapplication.di.module.NetworkModule
import com.example.newsapplication.mainFragments.*
import com.example.newsapplication.splashFragments.FirstFragment
import com.example.newsapplication.splashFragments.FourthFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DatabaseModule::class,NetworkModule::class])
interface AppComponent {
    fun intialize(homeFragment: HomeFragment)
    fun fragmentitemtype(fragment:ItemVpFragment)
    fun fragmentfourth(fragment:FourthFragment)
    fun first(fragment:FirstFragment)
    fun category(fragment:CategoryFragment)
    fun search(fragment:SearchFragment)
    fun saved(fragment:SavedFragment)
    fun info(fragment:InfoFragment)
}