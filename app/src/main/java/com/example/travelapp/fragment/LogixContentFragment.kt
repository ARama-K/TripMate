package com.example.travelapp.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.R
import com.example.travelapp.databinding.LogixContentFragmentBinding
import com.example.travelapp.viewmodel.DiaryViewModel

class LogixContentFragment : Fragment {
    var binding: LogixContentFragmentBinding? = null
    private var diaryViewModel: DiaryViewModel? = null
    var image: Bitmap? = null
    var id: String? = null

    constructor(img: Bitmap?, content: String?) {
        this.id = content
    }

    constructor()

    override fun onCreate(arg: Bundle?) {
        super.onCreate(arg)
        if (arguments != null) {
            this.id = requireArguments().getString("content")
        } else {
            this.image = null
            this.id = ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the View for this fragment using the binding
        binding = LogixContentFragmentBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        diaryViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create<DiaryViewModel>(
                DiaryViewModel::class.java
            )
        println(this.id)
        val id = id!!.toInt()
        val diary = diaryViewModel!!.findDiarybyId(id)
        if (id % 10 == 0) {
            binding!!.image.setImageResource(R.drawable.i1)
        } else if (id % 10 == 1) {
            binding!!.image.setImageResource(R.drawable.i2)
        } else if (id % 10 == 2) {
            binding!!.image.setImageResource(R.drawable.i3)
        } else if (id % 10 == 3) {
            binding!!.image.setImageResource(R.drawable.i4)
        } else if (id % 10 == 4) {
            binding!!.image.setImageResource(R.drawable.i5)
        } else if (id % 10 == 5) {
            binding!!.image.setImageResource(R.drawable.i6)
        } else if (id % 10 == 6) {
            binding!!.image.setImageResource(R.drawable.i7)
        } else if (id % 10 == 7) {
            binding!!.image.setImageResource(R.drawable.i8)
        } else if (id % 10 == 8) {
            binding!!.image.setImageResource(R.drawable.i9)
        } else if (id % 10 == 9) {
            binding!!.image.setImageResource(R.drawable.i10)
        }

        binding!!.title.text = diary!!.title
        binding!!.date.text = diary.date
        binding!!.fee.text = diary.fee.toString()
        binding!!.score.text = diary.rating.toString()
        binding!!.content.text = diary.description
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}