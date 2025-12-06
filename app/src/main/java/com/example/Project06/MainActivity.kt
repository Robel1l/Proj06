package com.example.Project06

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.Project06.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

// key for passing data between activities
const val ENTRY_EXTRA = "ENTRY_EXTRA"

class MainActivity : AppCompatActivity() {

    // view binding for activity
    private lateinit var main_binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // attach layout to view binding
        main_binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main_binding.root)

        // set up bottom navigation bar
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle tab changes in bottom nav bar
        bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.logTab -> LogFragment()
                R.id.dashboardTab -> DashboardFragment()
                else -> LogFragment()
            }
            // swap fragments and handle new food entry
            swapFragment(fragment)
            handleNewEntry()
            true
        }

        // default tab on nav bar
        bottomNav.selectedItemId = R.id.dashboardTab
    }

    // replace fragment in activity
    private fun swapFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    // check for new food entry and add to DB
    private fun handleNewEntry() {

        // check if there is a new food entry
        val food = intent.getSerializableExtra(ENTRY_EXTRA)
        if (food != null) {
            Log.d("MainActivity", "Received food entry")
            val displayFood = food as DisplayFood

            // add new food entry to DB
            lifecycleScope.launch(IO) {
                (application as FitApplication).db.foodDao().insert(
                    FoodEntity(
                        name = displayFood.name,
                        calories = displayFood.calories
                    )
                )
            }

            // prevent duplicate food entry's
            intent.removeExtra(ENTRY_EXTRA)
        }

        // Add Food button
        val addeats_Btn: Button = findViewById(R.id.button)
        addeats_Btn.setOnClickListener {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
        }
    }
}
