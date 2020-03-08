package com.yulmaso.vkauth.ui.auth

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.yulmaso.vkauth.base.BaseViewModel
import com.yulmaso.vkauth.data.Repository
import com.yulmaso.vkauth.util.Commands
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.util.Signal.Companion.FLAG_CAUSES_NAVIGATION
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
): BaseViewModel(application) {

    val vkConnectionState = repository.vkConnectionState

    init {
        repository.checkVkLogin()
    }

    fun authenticate(view: View){
        enqueueCommand(Commands.VK_LOGIN)
    }

}