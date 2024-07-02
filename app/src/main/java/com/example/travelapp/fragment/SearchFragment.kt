// SearchFragment.kt
package com.example.travelapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.travelapp.databinding.SearchFragmentBinding
import com.example.travelapp.retrofit.RetrofitClient
import com.example.travelapp.retrofit.RetrofitInterface
import com.example.travelapp.retrofit.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private var keyword: String? = null
    private var binding: SearchFragmentBinding? = null
    private var retrofitInterface: RetrofitInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        val view: View = binding!!.root
        retrofitInterface = RetrofitClient.retrofitService // Access the service directly

        binding!!.btnSearch.setOnClickListener {
            keyword = binding!!.editText.text.toString()
            val callAsync = retrofitInterface!!.customSearch(API_KEY, SEARCH_ID_cx, keyword ?: "")
            //makes an async request & invokes callback methods when the response returns
            callAsync!!.enqueue(object : Callback<SearchResponse> { // Removed !! from enqueue
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()?.items // Safe call to avoid NPE
                        val result = list?.get(0)?.snippet ?: "No results found" // Safe call
                        Log.i("Result", result)
                        binding!!.tvResult.text = result
                    } else {
                        Log.i("Error", "Response failed")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show() // Added show()
                }
            })
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val API_KEY = "AIzaSyCWt-cuEjmGZIwrO2XB3MkOHw4csbGKF8Y"
        private const val SEARCH_ID_cx = "96f39634a531a4079"
    }
}
