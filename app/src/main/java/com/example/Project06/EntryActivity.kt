package com.example.Project06

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EntryActivity : AppCompatActivity() {

    // ui elements for entry
    private lateinit var add_food: EditText
    private lateinit var add_cals: EditText
    private lateinit var sub_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        // Connect views to XMl elements
        add_food = findViewById(R.id.eats_E)
        add_cals = findViewById(R.id.add_calories)
        sub_btn = findViewById(R.id.subBn)

        // Handle button click
        sub_btn.setOnClickListener {
            Log.d("EntryActivity", "Submit clicked")

            val name = add_food.text.toString()
            val calories = add_cals.text.toString().toLongOrNull()

            // validate data and pass back to main activity
            if (name.isNotEmpty() && calories != null) {
                val food = DisplayFood(name, calories)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(ENTRY_EXTRA, food)
                startActivity(intent)
                finish()
            }
        }
    }
}
