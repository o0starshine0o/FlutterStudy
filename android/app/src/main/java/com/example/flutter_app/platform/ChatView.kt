package com.example.flutter_app.platform

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter_app.app.MyApp
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import io.flutter.plugin.platform.PlatformView

class ChatView(id: Int, private val info: ChatInfo, private val creationParams: Map<String?, Any?>?) : PlatformView {
    override fun getView(): View {
        // 注意: 虽然要的是context,但是内部会强转AppCompatActivity
        return ChatLayout(MyApp.compatActivity).apply {
            initDefault()
            chatInfo = info
        }
    }

    override fun dispose() {
    }
}