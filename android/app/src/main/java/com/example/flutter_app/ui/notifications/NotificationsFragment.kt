package com.example.flutter_app.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.flutter_app.R
import com.example.flutter_app.app.MyApp.Companion.TAG
import com.example.flutter_app.ui.home.HomeFragmentArgs
import com.tencent.imsdk.v2.V2TIMConversation
import com.tencent.imsdk.v2.V2TIMConversation.V2TIM_C2C
import com.tencent.imsdk.v2.V2TIMConversation.V2TIM_GROUP
//import com.tencent.imsdk.v2.V2TIMGroupAtInfo
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMValueCallback
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo
import com.tencent.qcloud.tim.uikit.modules.chat.layout.input.InputLayout
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment() {

    private val args by navArgs<HomeFragmentArgs>()
    private val info = ChatInfo()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatLayout.apply {
            initDefault()
            chatInfo = info.apply {
                type = if (args.isGroup) V2TIM_GROUP else V2TIM_C2C
                id = args.id
                chatName = args.title
            }
//            messageLayout.onItemClickListener = ItemClickListener()
//            inputLayout.setStartActivityListener(StartActivityListener())
//            V2TIMManager.getConversationManager().getConversation(args.conversationId, ConversationManager())
        }
    }


//    private fun updateAtInfoLayout() {
//        chatLayout.atInfoLayout.visibility = VISIBLE
//        when (getAtInfoType(info.atInfoList)) {
//            V2TIMGroupAtInfo.TIM_AT_ME -> chatLayout.atInfoLayout.text = getString(R.string.ui_at_me)
//            V2TIMGroupAtInfo.TIM_AT_ALL -> chatLayout.atInfoLayout.text = getString(R.string.ui_at_all)
//            V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME -> chatLayout.atInfoLayout.text = getString(R.string.ui_at_all_me)
//            else -> chatLayout.atInfoLayout.visibility = GONE
//        }
//    }
//
//    private fun getAtInfoType(atInfoList: List<V2TIMGroupAtInfo>?): Int {
//        var atInfoType = 0
//        var atMe = false
//        var atAll = false
//        if (atInfoList == null || atInfoList.isEmpty()) {
//            return V2TIMGroupAtInfo.TIM_AT_UNKNOWN
//        }
//        for (atInfo in atInfoList) {
//            if (atInfo.atType == V2TIMGroupAtInfo.TIM_AT_ME) {
//                atMe = true
//                continue
//            }
//            if (atInfo.atType == V2TIMGroupAtInfo.TIM_AT_ALL) {
//                atAll = true
//                continue
//            }
//            if (atInfo.atType == V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME) {
//                atMe = true
//                atAll = true
//                continue
//            }
//        }
//        atInfoType = if (atAll && atMe) {
//            V2TIMGroupAtInfo.TIM_AT_ALL_AT_ME
//        } else if (atAll) {
//            V2TIMGroupAtInfo.TIM_AT_ALL
//        } else if (atMe) {
//            V2TIMGroupAtInfo.TIM_AT_ME
//        } else {
//            V2TIMGroupAtInfo.TIM_AT_UNKNOWN
//        }
//        return atInfoType
//    }
//
//    inner class ItemClickListener : MessageLayout.OnItemClickListener {
//        override fun onMessageLongClick(view: View?, position: Int, messageInfo: MessageInfo?) {
//            chatLayout.messageLayout.showItemPopMenu(position - 1, messageInfo, view);
//        }
//
//        override fun onUserIconClick(view: View?, position: Int, messageInfo: MessageInfo?) {
//        }
//    }
//
//    inner class StartActivityListener : InputLayout.onStartActivityListener {
//        override fun onStartGroupMemberSelectActivity() {
//        }
//
//        override fun handleStartGroupLiveActivity(): Boolean {
//            return true
//        }
//    }
//
//    inner class ConversationManager: V2TIMValueCallback<V2TIMConversation>{
//        override fun onError(code: Int, desc: String?) {
//            Log.e(TAG, "getConversation error[$code]:$desc")
//        }
//
//        override fun onSuccess(conversation: V2TIMConversation?) {
//            if (conversation == null) return
//            info.atInfoList = conversation.groupAtInfoList
//            updateAtInfoLayout()
//        }
//    }
}