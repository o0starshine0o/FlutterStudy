package com.example.flutter_app.platform

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.platform.PlatformViewFactory

@Suppress("UNCHECKED_CAST")
class ChatLayoutFactory(private val activity: FragmentActivity,createArgsCodec: MessageCodec<Any>, private val chatInfo: ChatInfo) : PlatformViewFactory(createArgsCodec) {
    // 注意: 这里的context是SingleViewPresentation的PresentationContext,无法创建ChatView,需要AppCompatActivity
    // Presentation：它给Android提供了在对应的上下文（Context）和显示屏对象（Display）上绘制的能力，通常用于双屏异显
    override fun create(context: Context, viewId: Int, args: Any?) = ChatView(activity, viewId, chatInfo, args as Map<String?, Any?>?)
}