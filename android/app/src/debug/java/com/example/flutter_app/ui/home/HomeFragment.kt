package com.example.flutter_app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flutter_app.R
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val conversationLayout: ConversationLayout = root.findViewById(R.id.conversationLayout)
        conversationLayout.initDefault()
        conversationLayout.conversationList.init()
        conversationLayout.conversationList.setOnItemClickListener { view, position, messageInfo -> }
        return root
    }
}