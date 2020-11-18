package com.example.flutter_app.pigeon

import android.app.Activity
import android.content.Intent
import com.example.flutter_app.provider.C
import com.example.flutter_app.ui.main.IMActivity
import com.example.pigeon_plugin.Messages
import io.flutter.embedding.engine.plugins.FlutterPlugin

class MessageHandler(private val activity:Activity) : FlutterPlugin, Messages.Api {
    override fun search(request: Messages.SearchRequest?) = Messages.SearchReplay().apply { result = "${request?.query} + result" }
    override fun startIMActivity() = activity.startActivity(Intent(C.c, IMActivity::class.java))
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) = Messages.Api.setup(binding.binaryMessenger, this)
    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) = Messages.Api.setup(binding.binaryMessenger, null)
}