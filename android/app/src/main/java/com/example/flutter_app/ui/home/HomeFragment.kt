package com.example.flutter_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flutter_app.R
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conversationLayout.initDefault()
        conversationLayout.conversationList.init()
        conversationLayout.conversationList.setOnItemClickListener { _, _, info ->
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavNotifications(info.id, info.title, info.isGroup))
        }
    }
}