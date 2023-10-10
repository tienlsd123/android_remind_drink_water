package com.bxt.reminddrinkwater.util

import androidx.viewpager2.widget.ViewPager2
import com.bxt.reminddrinkwater.adapter.Page
import com.google.android.material.bottomnavigation.BottomNavigationView

fun ViewPager2.registerOnPageChange(botNav: BottomNavigationView) {
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                Page.HOME.index -> botNav.menu.findItem(Page.HOME.itemId).isChecked = true
                Page.NOTIFICATION.index -> botNav.menu.findItem(Page.NOTIFICATION.itemId).isChecked = true
            }
        }
    })
}

fun BottomNavigationView.setSelectItem(viewPager2: ViewPager2) {
    setOnItemSelectedListener {
        when (it.itemId) {
            Page.HOME.itemId -> viewPager2.currentItem = Page.HOME.index
            Page.NOTIFICATION.itemId -> viewPager2.currentItem = Page.NOTIFICATION.index
        }
        true
    }
}


