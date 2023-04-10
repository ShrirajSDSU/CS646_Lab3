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