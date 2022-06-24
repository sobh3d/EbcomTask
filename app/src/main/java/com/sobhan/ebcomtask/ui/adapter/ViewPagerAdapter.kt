package com.sobhan.ebcomtask.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sobhan.ebcomtask.ui.screens.message.MessagesFragment
import com.sobhan.ebcomtask.ui.screens.message.SavedMessagesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> MessagesFragment()
            0 -> SavedMessagesFragment()
            else -> MessagesFragment()
        }
    }
}
