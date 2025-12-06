package com.example.Project06

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LogFragment : Fragment() {

    // holds list of food in recycler view
    private val fooditemList = mutableListOf<DisplayFood>()
    private lateinit var foodRe_V: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // inflate the layout
        val view = inflater.inflate(R.layout.fragment_log, container, false)

        // set up the recycler view and connect the adapter
        foodRe_V = view.findViewById(R.id.food_re_v)
        val adapter = BitFitAdapter(requireContext(), fooditemList)
        foodRe_V.adapter = adapter

        // set up the layout manager to display items vertically
        foodRe_V.layoutManager = LinearLayoutManager(requireContext()).also {
            foodRe_V.addItemDecoration(
                DividerItemDecoration(requireContext(), it.orientation)
            )
        }

        // Observe the changes in the database and update the UI
        lifecycleScope.launchWhenStarted {
            (activity?.application as FitApplication)
                .db.foodDao()
                .getAll()
                .collect { list ->

                    val mappedList = list.map { DisplayFood(it.name, it.calories) }

                    fooditemList.clear()
                    fooditemList.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
        }

        return view
    }
}
