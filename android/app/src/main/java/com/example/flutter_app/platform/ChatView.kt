package com.example.flutter_app.platform

import androidx.fragment.app.FragmentActivity
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import io.flutter.plugin.platform.PlatformView

class ChatView(private val activity: FragmentActivity, id: Int, private val info: ChatInfo, private val creationParams: Map<String?, Any?>?) : PlatformView {
    private val chatLayout = ChatLayout(activity).apply {
        initDefault()
        chatInfo = info
    }
    override fun getView() = chatLayout

    override fun dispose() {
    }
}