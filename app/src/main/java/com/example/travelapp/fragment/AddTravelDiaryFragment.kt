package com.example.travelapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.SelectMapActivity
import com.example.travelapp.databinding.AddTravelDiaryFragmentBinding
import com.example.travelapp.entity.DiaryEntry
import com.example.travelapp.viewmodel.DiaryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTravelDiaryFragment : Fragment() {
    private var titleEditText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var locationEditText: EditText? = null
    private var expenseEditText: EditText? = null
    private var datePicker: DatePicker? = null

    private var satisfactionSeekBar: SeekBar? = null
    private var satisfactionScoreTextView: TextView? = null
    private var weatherRadioGroup: RadioGroup? = null
    private var binding: AddTravelDiaryFragmentBinding? = null
    private var diaryViewModel: DiaryViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTravelDiaryFragmentBinding.inflate(inflater, container, false)
        val view: View = binding!!.root

        titleEditText = binding!!.titleEdittext
        descriptionEditText = binding!!.descriptionEdittext
        locationEditText = binding!!.locationEdittext
        expenseEditText = binding!!.expenseEdittext
        datePicker = binding!!.datePicker
        satisfactionSeekBar = binding!!.satisfactionSlider
        satisfactionScoreTextView = binding!!.satisfactionScoreTextview
        weatherRadioGroup = binding!!.weatherRadiogroup
        satisfactionSeekBar!!.max = 10
        satisfactionSeekBar!!.progress = 5
        satisfactionScoreTextView!!.text = "5"
        satisfactionSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                satisfactionScoreTextView!!.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        diaryViewModel = ViewModelProvider(requireActivity()).get(DiaryViewModel::class.java)

        val saveButton: Button = binding!!.saveButton
        saveButton.setOnClickListener {
            if (TextUtils.isEmpty(titleEditText!!.text)) {
                titleEditText!!.error = "Title is required."
                val msg = "Title is required"
                toastMsg(msg)
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(expenseEditText!!.text)) {
                expenseEditText!!.error = "Expense is required."
                val msg = "Expense is required"
                toastMsg(msg)
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(descriptionEditText!!.text)) {
                descriptionEditText!!.error = "Description is required."
                val msg = "Description is required."
                toastMsg(msg)
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(locationEditText!!.text)) {
                locationEditText!!.error = "Location is required."
                val msg = "Location is required."
                toastMsg(msg)
                return@setOnClickListener
            }

            if (weatherRadioGroup!!.checkedRadioButtonId == -1) {
                Toast.makeText(context, "Please select weather.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val title = titleEditText!!.text.toString().trim { it <= ' ' }
            val description = descriptionEditText!!.text.toString().trim { it <= ' ' }
            val location = locationEditText!!.text.toString().trim { it <= ' ' }
            val expense = expenseEditText!!.text.toString().toInt()
            val satisfaction = satisfactionSeekBar!!.progress
            val weatherId = weatherRadioGroup!!.checkedRadioButtonId
            val weatherRadioButton = view.findViewById<RadioButton>(weatherId)
            val weather = weatherRadioButton.text.toString()
            val day = datePicker!!.dayOfMonth
            val month = datePicker!!.month
            val year = datePicker!!.year
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val date = calendar.time
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val dateString = sdf.format(date)

            val newDiary = DiaryEntry(
                title = title,
                date = dateString,
                description = description,
                weather = weather,
                location = location,
                fee = expense,
                rating = satisfaction
            )
            diaryViewModel!!.insert(newDiary)
            toastMsg("Add successful")
        }

        binding!!.mapbutton.setOnClickListener {
            val location = locationEditText!!.text.toString()
            if (location == "") {
                Toast.makeText(context, "Type any location", Toast.LENGTH_SHORT).show()
            } else {
                val preferences =
                    requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("location", location)
                editor.apply()
                val intent = Intent(context, SelectMapActivity::class.java)
                startActivity(intent)
            }
        }

        binding!!.clearButton.setOnClickListener {
            titleEditText!!.setText("")
            descriptionEditText!!.setText("")
            locationEditText!!.setText("")
            expenseEditText!!.setText("")
            satisfactionSeekBar!!.progress = 5
            satisfactionScoreTextView!!.text = "5"
            val sunnyRadioButton: RadioButton = binding!!.sunnyRadiobutton
            weatherRadioGroup!!.check(sunnyRadioButton.id)
        }

        diaryViewModel!!.allDiary.observe(viewLifecycleOwner, Observer { customers ->
            var allCustomers = ""
            for (temp in customers) {
                val customerDetails = "${temp.title} ${temp.date} ${temp.weather} ${temp.location}"
                allCustomers = allCustomers +
                        System.getProperty("line.separator") + customerDetails
            }
        })

        return view
    }

    private fun toastMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}