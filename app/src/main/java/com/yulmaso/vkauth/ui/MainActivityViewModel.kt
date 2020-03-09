package com.yulmaso.vkauth.ui

import androidx.lifecycle.ViewModel
import com.yulmaso.vkauth.data.Repository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun checkVkLogin() = repository.checkVkLogin()

    fun setUserId(profileId: Int) = repository.setUserId(profileId)

    fun setVkConnectionState(state: Int) = repository.setVkConnectionState(state)

}