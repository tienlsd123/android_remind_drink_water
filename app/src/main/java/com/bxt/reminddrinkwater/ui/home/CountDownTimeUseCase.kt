package com.bxt.reminddrinkwater.ui.home

import com.bxt.reminddrinkwater.util.convertTimeShow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class CountDownTimeUseCase @Inject constructor() {

    operator fun invoke(): Flow<String> = flow {
        setupCountDown()
    }

    private suspend fun FlowCollector<String>.setupCountDown() {
        val now = Calendar.getInstance()
        val plusToNextTime = if (now.get(Calendar.HOUR_OF_DAY) % 2 == 0) 2 else 1
        val nextTime = Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, plusToNextTime)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }
        var timeLeft = nextTime.timeInMillis - now.timeInMillis
        while (timeLeft >= 0) {
            val seconds = ((timeLeft / 1000) % 60).toInt()
            val minutes = ((timeLeft / (1000 * 60)) % 60).toInt()
            val hours = (timeLeft / (1000 * 60 * 60)).toInt()
            emit("${hours.convertTimeShow()}:${minutes.convertTimeShow()}:${seconds.convertTimeShow()}")
            delay(1000)
            timeLeft -= 1000
        }
        setupCountDown()
    }
}
