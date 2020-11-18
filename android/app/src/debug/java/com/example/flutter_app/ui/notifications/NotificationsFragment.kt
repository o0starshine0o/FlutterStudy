package com.example.flutter_app.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flutter_app.R
import com.tencent.imsdk.v2.V2TIMConversation.V2TIM_C2C
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val chatLayout: ChatLayout = root.findViewById(R.id.chatLayout)
//        chatLayout.initDefault()
        chatLayout.chatInfo = ChatInfo().apply {
            type = V2TIM_C2C
            id = "123"
            chatName = "测试聊天"
        }
        chatLayout.initDefault()
        return root
    }
}