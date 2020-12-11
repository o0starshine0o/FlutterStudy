package com.example.flutter_app.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.flutter_app.R
import com.example.flutter_app.app.MyApp
import com.example.flutter_app.utils.UserSignUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack

class IMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_i_m)
        val navView = findViewById<BottomNavigationView>(R.id.navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navNotifications -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }


        val userId = "123321"
        val userSign = UserSignUtils.generalUserSign(userId)
        TUIKit.login(userId, userSign, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) = Unit.apply { Log.i(MyApp.TAG, "user login onSuccess: $data") }
            override fun onError(module: String?, errCode: Int, errMsg: String?) = Unit.apply { Log.e(MyApp.TAG, "user login onError($module)[$errCode]: $errMsg") }
        })
    }
}