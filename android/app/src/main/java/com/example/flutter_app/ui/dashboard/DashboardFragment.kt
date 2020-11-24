package com.example.flutter_app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flutter_app.R
import com.example.flutter_app.ui.contact.AddMoreActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contactLayout.initDefault()
        contactLayout.contactListView.setOnItemClickListener { position, contact ->
            when (position) {
                0, 1, 2 -> startActivity(Intent(activity, AddMoreActivity::class.java))
                else -> findNavController().navigate(DashboardFragmentDirections.actionNavDashboardToNavNotifications(contact.id, contact.nickname, contact.isGroup))
            }
        }
    }
}