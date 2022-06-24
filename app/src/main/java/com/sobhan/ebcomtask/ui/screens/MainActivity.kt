package com.sobhan.ebcomtask.ui.screens


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator
import com.sobhan.ebcomtask.R
import com.sobhan.ebcomtask.databinding.ActivityMainBinding
import com.sobhan.ebcomtask.ui.adapter.ViewPagerAdapter


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var badge: BadgeDrawable
    private val messageViewModel: MessageViewModel by viewModels()
    private var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewPager()
        setupTabLayout()
        messageViewModel.messages.observe(this, Observer {
            num = 0
            if (!it.isNullOrEmpty()) {
                it.forEach { item ->
                    if (item.unread) {
                        num += 1
                    }
                }

            }

            messageViewModel.unreadMessageCount(num)

        })

        messageViewModel.unreadMessagesCount.observe(this, Observer {
            if(it==0){
                badge.isVisible = false
            }else{
                badge.number = it
                badge.isVisible = true
            }


        })

    }


    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                1 -> {
                    badge = tab.orCreateBadge
                    badge.badgeGravity = BadgeDrawable.BOTTOM_START
                    badge.horizontalOffset = -25
                    badge.verticalOffset = 25
                    badge.isVisible = false
                    tab.text = getString(R.string.public_messages)
                }
                0 -> {
                    tab.text = getString(R.string.saved_messages)
                }
            }
        }.attach()
        binding.viewPager.currentItem = 1
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this, 2)
        binding.viewPager.adapter = adapter
    }

}