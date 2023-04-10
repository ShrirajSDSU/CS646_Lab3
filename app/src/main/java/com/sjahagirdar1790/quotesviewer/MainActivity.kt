/*

This Kotlin code defines a package named "com.sjahagirdar1790.quotesviewer".
It imports necessary modules such as "AppCompatActivity", "Bundle", and "QuotesFragment".
The "MainActivity" class extends the "AppCompatActivity" class.
It overrides the "onCreate" method and sets the content view for the activity.
It then checks if there is a saved instance state and creates a new instance of the "QuotesFragment" class
and replaces the container with the fragment using a transaction that is committed immediately.
*/
package com.sjahagirdar1790.quotesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sjahagirdar1790.quotesviewer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuotesFragment.newInstance())
                .commitNow()
        }
    }
}