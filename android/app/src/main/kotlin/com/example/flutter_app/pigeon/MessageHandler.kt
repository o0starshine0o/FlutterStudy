package com.example.flutter_app.pigeon

import com.example.pigeon_plugin.Messages
import io.flutter.embedding.engine.plugins.FlutterPlugin

class MessageHandler : FlutterPlugin, Messages.Api {
    override fun search(request: Messages.SearchRequest?) = Messages.SearchReplay().apply { result = "${request?.query} + result" }
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) = Messages.Api.setup(binding.binaryMessenger, this)
    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) = Messages.Api.setup(binding.binaryMessenger, null)
}