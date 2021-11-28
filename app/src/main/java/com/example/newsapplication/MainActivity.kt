package com.example.newsapplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.utils.MySharedPreference
import com.example.newsapplication.utils.ThemeHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
          navController.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
              override fun onDestinationChanged(
                  controller: NavController,
                  destination: NavDestination,
                  arguments: Bundle?
              ) {
                  when(destination.id){
                      R.id.homeFragment->{
                          showbottombar()
                      }
                      R.id.savedFragment->{
                          showbottombar()
                      }
                      R.id.categoryFragment->{
                          showbottombar()
                      }
                      R.id.settingsFragment->{
                          showbottombar()
                      } else ->hidebottombar()
                  }
              }

          })


        navView.setupWithNavController(navController)
    }

    fun showbottombar(){
        binding.navView.animate().translationY(0f)
    }

    fun hidebottombar(){
        binding.navView.animate().translationY(binding.navView.height.toFloat())
    }
}