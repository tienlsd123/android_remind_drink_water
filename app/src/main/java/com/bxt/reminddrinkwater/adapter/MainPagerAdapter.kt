package com.bxt.reminddrinkwater.adapter

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.screen.home.HomeFragment
import com.bxt.reminddrinkwater.screen.notification.NotificationFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = Page.entries.size

    override fun createFragment(position: Int) = Page.entries[position].newInstance()
}

enum class Page(val index: Int, @IdRes val itemId: Int, val newInstance: () -> Fragment) {
    HOME(index = 0, itemId = R.id.item_home, newInstance = HomeFragment::newInstance),
    NOTIFICATION(index = 1, itemId = R.id.item_notification, newInstance = NotificationFragment::newInstance)
}
