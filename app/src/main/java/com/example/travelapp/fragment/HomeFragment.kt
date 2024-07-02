package com.example.travelapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.databinding.ActivityHomeFragmentBinding
import com.example.travelapp.viewmodel.DiaryViewModel

class HomeFragment : Fragment() {
    private var binding: ActivityHomeFragmentBinding? = null
    private var adapter: HomeRecyclerViewAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var diaryViewModel: DiaryViewModel? = null
    override fun onCreate(arg: Bundle?) {
        super.onCreate(arg)
        diaryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create<DiaryViewModel>(
                DiaryViewModel::class.java
            )
        diaryViewModel!!.SynchronizeGetData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the View for this fragment
        binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)
        val view: View = binding!!.getRoot()

        val diaryList = diaryViewModel!!.allId
        adapter = HomeRecyclerViewAdapter(diaryViewModel!!, diaryList.value)
        //this just creates a line divider between rows
        binding!!.recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        binding!!.recyclerView.setAdapter(adapter)

        layoutManager = LinearLayoutManager(activity)
        binding!!.recyclerView.setLayoutManager(layoutManager)
        diaryList.observe(viewLifecycleOwner, object : Observer<List<Int?>?> {
            override fun onChanged(value: List<Int?>?) {
                adapter = HomeRecyclerViewAdapter(diaryViewModel!!, value)
                //this just creates a line divider between rows
                binding!!.recyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        LinearLayoutManager.VERTICAL
                    )
                )
                binding!!.recyclerView.setAdapter(adapter)
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}