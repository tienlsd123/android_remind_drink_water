package com.bxt.reminddrinkwater.ui.main

import androidx.lifecycle.ViewModel
import com.bxt.reminddrinkwater.data.MessageDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject internal constructor(private val messageDao: MessageDao) : ViewModel() {
}
