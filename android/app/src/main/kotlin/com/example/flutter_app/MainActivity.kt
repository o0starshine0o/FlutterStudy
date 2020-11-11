package com.example.flutter_app

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    companion object{
        // 这是通道名称
        private const val butteryChannel = "com.example.flutter_app/battery"
        // 这是通道里面方法的名称
        private const val butteryMethod = "getBattery"
    }
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        // 注册方法通道
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, butteryChannel).setMethodCallHandler(this::butteryChannelHandler)
    }

    private fun butteryChannelHandler(call: MethodCall, result: MethodChannel.Result) {
        // flutter要调用原生的方法
        when (call.method) {
            butteryMethod -> getBatteryLevel(result)
            else -> result.notImplemented()
        }
    }

    private fun getBatteryLevel(result: MethodChannel.Result) {
        // 原生方法返回
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            result.success(batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY))
        } else {
            ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))?.apply {
                result.success(getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / getIntExtra(BatteryManager.EXTRA_SCALE, -1))
            } ?: result.error("", "", "")
        }
    }

}
