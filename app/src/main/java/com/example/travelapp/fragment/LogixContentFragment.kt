package com.example.travelapp.fragment

import android.graphics.Bitmap
import android.os.Bundle

import androidx.fragment.app.Fragment


class LogixContentFragment : Fragment {
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

}
