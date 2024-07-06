package com.example.travelapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.travelapp.User1.Use
import com.example.travelapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAppBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        setSupportActionBar(binding.appBar.toolbar)
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home_fragment
        ).setOpenableLayout(binding.drawerLayout).build()

        val fragmentManager: FragmentManager = supportFragmentManager
        val navHostFragment: NavHostFragment = fragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.appBar.toolbar, navController, mAppBarConfiguration)
    }

    override fun onDestroy() {
        Use.setEmail("")
        super.onDestroy()
        //Use.setEmail("")
    }
}