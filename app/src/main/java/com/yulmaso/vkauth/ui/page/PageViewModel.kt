package com.yulmaso.vkauth.ui.page

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import com.yulmaso.vkauth.base.BaseViewModel
import com.yulmaso.vkauth.data.Repository
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.data.vk.VKFriendsRequest
import com.yulmaso.vkauth.data.vk.VKUser
import com.yulmaso.vkauth.data.vk.VKUserRequest
import com.yulmaso.vkauth.util.AUTH_PREFERENCES
import com.yulmaso.vkauth.util.Commands
import com.yulmaso.vkauth.util.Signal.Companion.FLAG_CAUSES_NAVIGATION
import com.yulmaso.vkauth.util.VK_USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PageViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
): BaseViewModel(application) {

    init {
        repository.checkVkLogin()
    }

    val vkConnectionState = repository.vkConnectionState
    val friends = repository.friends
    val profileInfo = repository.profileInfo

    fun refresh(){
        repository.refreshFriends()
    }

    fun vkLogOut(){
        repository.vkLogOut()
    }

}