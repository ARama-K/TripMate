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
import com.example.travelapp.entity.DiaryEntry
import com.example.travelapp.viewmodel.DiaryViewModel

class HomeRecyclerViewAdapter(
    private val diaryViewModel: DiaryViewModel,
    private val diaryListId: List<Int?>?
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
        val idDiary = diaryListId?.get(position)
        if (idDiary != null) {
            val diary = diaryViewModel.findDiarybyId(idDiary)
            if (diary != null) {
                // Set the image based on the diary ID modulo 10
                when (diary.id!! % 10) {
                    0 -> viewHolder.binding.image.setImageResource(R.drawable.i1)
                    1 -> viewHolder.binding.image.setImageResource(R.drawable.i2)
                    2 -> viewHolder.binding.image.setImageResource(R.drawable.i3)
                    3 -> viewHolder.binding.image.setImageResource(R.drawable.i4)
                    4 -> viewHolder.binding.image.setImageResource(R.drawable.i5)
                    5 -> viewHolder.binding.image.setImageResource(R.drawable.i6)
                    6 -> viewHolder.binding.image.setImageResource(R.drawable.i7)
                    7 -> viewHolder.binding.image.setImageResource(R.drawable.i8)
                    8 -> viewHolder.binding.image.setImageResource(R.drawable.i9)
                    9 -> viewHolder.binding.image.setImageResource(R.drawable.i10)
                }

                // Updating UI with diary details
                viewHolder.binding.logixTiltle.text = diary.title
                viewHolder.binding.logixIntroduce.text = diary.description
                viewHolder.binding.cardView.setOnClickListener { v ->
                    val arg = Bundle().apply {
                        putString("content", diary.id.toString())
                    }
                    replaceFragment(arg, v)
                }
            } else {
                // Handle the case where diary is null
                viewHolder.binding.logixTiltle.text = "Diary not found"
                viewHolder.binding.logixIntroduce.text = ""
            }
        } else {
            // Handle the case where idDiary is null
            viewHolder.binding.logixTiltle.text = "ID not available"
            viewHolder.binding.logixIntroduce.text = ""
        }
    }

    private fun replaceFragment(arg: Bundle, view: View) {
        val fragmentActivity = view.context as FragmentActivity
        val fragmentManager = fragmentActivity.supportFragmentManager
        val navHostFragment =
            fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        navController.navigate(R.id.logixContentFragment, arg)
    }

    override fun getItemCount(): Int {
        if (diaryListId == null) return 0
        return diaryListId.size
    }

    class ViewHolder(val binding: HomeCardLayoutBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}
