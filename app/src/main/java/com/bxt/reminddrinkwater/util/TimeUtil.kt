package com.bxt.reminddrinkwater.util

import java.util.Calendar

fun getCalendarNextTime(plusToNextTime: Int): Calendar = Calendar.getInstance().apply {
    add(Calendar.HOUR_OF_DAY, plusToNextTime)
    set(Calendar.MINUTE, 0)
    set(Calendar.MILLISECOND, 0)
}