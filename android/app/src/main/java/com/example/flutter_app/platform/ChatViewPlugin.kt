package com.example.flutter_app.platform

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.flutter_app.app.MyApp.Companion.TAG
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.StandardMessageCodec

class ChatViewPlugin(private val activity: FragmentActivity,private val info: ChatInfo) : FlutterPlugin {
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        Log.i(TAG, "onAttachedToEngine")
        binding.platformViewRegistry.registerViewFactory("ChatLayout", ChatLayoutFactory(activity, StandardMessageCodec(), info))
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        Log.i(TAG, "onDetachedFromEngine")
    }
}