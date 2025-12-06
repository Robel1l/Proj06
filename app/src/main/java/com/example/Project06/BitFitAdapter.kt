package com.example.Project06

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BitFitAdapter(
    private val context: Context,
    private val foodList: List<DisplayFood>
) : RecyclerView.Adapter<BitFitAdapter.ViewHolder>() {

    // create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    // bind data to a view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = foodList[position]
        holder.bind(fooditem)
    }

    // return the total count of items
    override fun getItemCount() = foodList.size

   // holds each and manage views being used in the recycler view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val eats_TextV: TextView = itemView.findViewById(R.id.foodName)
        private val cals_TextV: TextView = itemView.findViewById(R.id.foodCalories)

        fun bind(food: DisplayFood) {
            eats_TextV.text = food.name
            cals_TextV.text = food.calories?.toString() ?: "-"
        }
    }
}