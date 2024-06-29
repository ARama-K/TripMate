package com.example.travelapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.databinding.HomeCardLayoutBinding


abstract class HomeRecyclerViewAdapter(
) : RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            HomeCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // this method binds the view holder created with data that will be displayed
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    private fun replaceFragment(arg: Bundle, view: View) {
        val fragmentActivity = view.context as FragmentActivity
        val fragmentManager = fragmentActivity.supportFragmentManager
        val navHostFragment =
            fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        navController.navigate(R.id.logixContentFragment, arg)
    }
    class ViewHolder(val binding: HomeCardLayoutBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}
