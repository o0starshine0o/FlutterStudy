package com.example.flutter_app.ui.main

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import com.example.flutter_app.pigeon.MessageHandler
import com.example.flutter_app.platform.ChatViewPlugin
import com.example.pigeon_plugin.Messages
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterFragmentActivity() {
    companion object {
        // 这是通道名称
        private const val butteryChannel = "com.example.flutter_app/battery"

        // 这是通道里面方法的名称
        private const val butteryMethod = "getBattery"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        // 注册方法通道
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, butteryChannel).setMethodCallHandler(this::butteryChannelHandler)
        // 使用pigeon来完成通信
        Messages.Api.setup(flutterEngine.dartExecutor.binaryMessenger, MessageHandler(this))
        // 注册原生控件,供flutter使用过
        flutterEngine.plugins.add(ChatViewPlugin(this, ChatInfo().apply {
            id = "123456"
            chatName = "测试EmbedView"
            isTopChat = false
        }))
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
