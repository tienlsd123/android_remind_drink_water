package com.bxt.reminddrinkwater.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.util.PreferencesKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    countDownTimeUseCase: CountDownTimeUseCase
) : ViewModel() {

    private val _userName: MutableLiveData<String> = MutableLiveData<String>("Trang")
    val userName: LiveData<String> = _userName

    val avatar: Flow<Any> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.PATH_AVATAR] ?: R.drawable.img_user
    }

    val countDownTimeFlow: Flow<String> = countDownTimeUseCase()


    fun saveImagePathToDataStore(path: String) = viewModelScope.launch {
        dataStore.edit { preferences -> preferences[PreferencesKeys.PATH_AVATAR] = path }
    }
}
