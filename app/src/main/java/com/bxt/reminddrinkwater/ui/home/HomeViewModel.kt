package com.bxt.reminddrinkwater.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bxt.reminddrinkwater.util.convertTimeShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(countDownTimeUseCase: CountDownTimeUseCase) : ViewModel() {

    private val _userName: MutableLiveData<String> = MutableLiveData<String>("Trang")
    val userName: LiveData<String> = _userName

    val countDownTimeFlow: Flow<String> = countDownTimeUseCase()

}
