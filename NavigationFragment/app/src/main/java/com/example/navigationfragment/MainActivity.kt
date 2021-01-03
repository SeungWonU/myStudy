package com.example.navigationfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.bottomNavigationView),findNavController(R.id.my_nav_host_fragment))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.london_screen) {
                Navigation.findNavController(view).navigate(R.id.action_italy_screen_to_paris_screen)

            } else if(destination.id == R.id.italy_screen){

            }
            else if(destination.id == R.id.paris_screen){

            }
        }


    }



}