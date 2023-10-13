package com.bxt.reminddrinkwater.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bxt.reminddrinkwater.data.Message
import com.bxt.reminddrinkwater.data.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {
    private val _listMsg = MutableLiveData<List<Message>>()
    val listMsg : LiveData<List<Message>> = _listMsg

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _listMsg.postValue(messageRepository.getMessages())
        }
    }
}
