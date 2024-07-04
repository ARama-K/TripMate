package com.example.travelapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.databinding.BarFragmentBinding
import com.example.travelapp.viewmodel.DiaryViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BarFragment : Fragment() {
    private var _binding: BarFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var diaryViewModel: DiaryViewModel
    private lateinit var datePickerBegin: DatePicker
    private lateinit var datePickerEnd: DatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BarFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        datePickerBegin = binding.dateBarPickerBegin
        datePickerEnd = binding.dateBarPickerEnd

        diaryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(DiaryViewModel::class.java)

        binding.SetButton.setOnClickListener {
            val beginDay = datePickerBegin.dayOfMonth
            val beginMonth = datePickerBegin.month
            val beginYear = datePickerBegin.year
            val beginCalendar = Calendar.getInstance().apply {
                set(beginYear, beginMonth, beginDay)
            }
            val beginDate = beginCalendar.time

            val endDay = datePickerEnd.dayOfMonth
            val endMonth = datePickerEnd.month
            val endYear = datePickerEnd.year
            val endCalendar = Calendar.getInstance().apply {
                set(endYear, endMonth, endDay)
            }
            val endDate = endCalendar.time

            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            var isChange = false
            val barEntriesDB = mutableListOf<BarEntry>()
            val locationList = mutableListOf<String>()

            val dateList = diaryViewModel.date
            val expenseList = diaryViewModel.fee
            val locationData = diaryViewModel.location

            for (i in dateList.indices) {
                try {
                    val dateDB = sdf.parse(dateList[i])
                    if (dateDB != null && !dateDB.before(beginDate) && !endDate.before(dateDB)) {
                        barEntriesDB.add(BarEntry(i.toFloat(), expenseList[i]?.toFloat() ?: 0f))
                        locationList.add(locationData[i] ?: "Unknown")
                        isChange = true
                    }
                } catch (e: ParseException) {
                    throw RuntimeException(e)
                }
            }

            barSetting(barEntriesDB, locationList)
            if (!isChange) {
                toastMsg("There is no data during this period")
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun barSetting(barEntries: List<BarEntry>, locationList: List<String>) {
        val barDataSet = BarDataSet(barEntries, "Travel Cost")
        barDataSet.colors = ColorTemplate.PASTEL_COLORS.toList()
        barDataSet.valueTextSize = 10f

        // Custom ValueFormatter to display location labels inside bars
        barDataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String {
                val index = barEntry?.x?.toInt() ?: 0
                return locationList.getOrElse(index) { "Unknown" }
            }
        }

        val barData = BarData(barDataSet)
        binding.barChart.data = barData

        // Y-axis settings
        binding.barChart.axisLeft.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%,d", value.toInt())
            }
        }
        binding.barChart.axisLeft.granularity = 10000f // Set the interval for Y-axis values
        binding.barChart.axisLeft.axisMinimum = 0f // Start from zero
        binding.barChart.axisLeft.axisMaximum = barEntries.maxOfOrNull { it.y }?.let { it + 10000f } ?: 10000f // Ensure the maximum is slightly above the highest bar
        binding.barChart.axisLeft.isEnabled = true

        binding.barChart.xAxis.isEnabled = false // Disable X-axis since labels are inside bars
        binding.barChart.axisRight.isEnabled = false // Hide right Y-axis
        binding.barChart.description.isEnabled = false // Hide the description
        binding.barChart.setFitBars(true) // Make the bars fit into the chart

        binding.barChart.invalidate() // Refresh the chart
    }

    private fun toastMsg(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
