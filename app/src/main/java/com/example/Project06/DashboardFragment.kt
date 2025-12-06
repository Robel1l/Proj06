package com.example.Project06

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class DashboardFragment : Fragment() {

    // Ui elements for calories calculations
    private lateinit var avg_View: TextView
    private lateinit var max_v: TextView
    private lateinit var min_v: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // connect views
        avg_View = view.findViewById(R.id.avgTV)
        min_v = view.findViewById(R.id.minTV)
        max_v = view.findViewById(R.id.maxTV)

        // Observe DB changes using Flow
        lifecycleScope.launch {
            (activity?.application as FitApplication).db.foodDao()
                .getAll()
                .collectLatest { items ->
                    val displayList = items.map { DisplayFood(it.name, it.calories) }
                    updateStatus(displayList)
                }
        }

        // clear button
        val clearButton: Button = view.findViewById(R.id.clear_Btn)

        // delete all previous entries
        clearButton.setOnClickListener {
            lifecycleScope.launch(IO) {
                (activity?.application as FitApplication).db.foodDao().deleteAll()
            }
        }

        return view
    }


    // update the calories calculations
    private fun updateStatus(foods: List<DisplayFood>) {
        // no data if db is empty
        if (foods.isEmpty()) {
            avg_View.text = "No Data"
            min_v.text = "No Data"
            max_v.text = "No Data"
            return
        }

        // calculate avg, min, max calories
        val calories = foods.mapNotNull { it.calories }
        val avg = calories.sum() / calories.size
        val min = calories.minOrNull()
        val max = calories.maxOrNull()

        // display data
        avg_View.text = avg.toString()
        min_v.text = min?.toString() ?: "No Data"
        max_v.text = max?.toString() ?: "No Data"
    }
}
