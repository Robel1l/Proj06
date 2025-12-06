package com.example.Project06

import android.app.Application

class FitApplication : Application() {
    val db by lazy { AppDatabase.getDb(this) }
}