package com.example.flutter_app.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter_app.R
import com.example.flutter_app.app.MyApp.Companion.compatActivity

class LeakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 让全局APP持有这个activity,虽然会泄漏,但是可以全局使用AppCompatActivity
        compatActivity = this
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}