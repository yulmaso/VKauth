package com.yulmaso.vkauth.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKTokenExpiredHandler
import com.yulmaso.vkauth.data.vk.VKFriendsRequest
import com.yulmaso.vkauth.data.vk.VKUser
import com.yulmaso.vkauth.data.vk.VKUserRequest
import com.yulmaso.vkauth.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class Repository @Inject constructor(
    private val dataProvider: DataProvider
) {

    private val vkConnectionStateMLive = MutableLiveData<Int>()
    val vkConnectionState: LiveData<Int> = vkConnectionStateMLive

    private val friendsMLive = MutableLiveData<List<VKUser>>()
    val friends: LiveData<List<VKUser>> = friendsMLive

    private val profileMLive = MutableLiveData<VKUser>()
    val profileInfo: LiveData<VKUser> = profileMLive

    fun setUserId(userId: Int) = dataProvider.setUserId(userId)

    fun getUserId() = dataProvider.getUserId()

    fun setVkConnectionState(state: Int) {
        vkConnectionStateMLive.value = state
    }

    fun refreshFriends() {
        VK.execute(VKFriendsRequest(), object : VKApiCallback<List<VKUser>> {
            override fun fail(error: Exception) {
                vkConnectionStateMLive.value = STATE_ERROR
                checkVkLogin()
            }

            override fun success(result: List<VKUser>) {
                //в задании нужно было пять
                friendsMLive.value = getFiveRandomFriends(result)
            }
        })
    }

    fun refreshProfileInfo(id: Int) {
        if (id != 0) {
            VK.execute(VKUserRequest(id), object : VKApiCallback<VKUser> {
                override fun fail(error: Exception) {
                    checkVkLogin()
                }

                override fun success(result: VKUser) {
                    profileMLive.value = result
                }
            })
        }
    }

    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            vkConnectionStateMLive.value = STATE_LOGGED_OUT
        }
    }

    fun checkVkLogin() {
        VK.addTokenExpiredHandler(tokenTracker)
        if (VK.isLoggedIn()) {
            if (vkConnectionState.value == STATE_LOGGED_OUT ||
                vkConnectionState.value == STATE_ERROR ||
                vkConnectionState.value == null) {
                refreshProfileInfo(getUserId())
                refreshFriends()
            }
            vkConnectionStateMLive.value = STATE_LOGGED_IN
        } else {
            vkConnectionStateMLive.value = STATE_LOGGED_OUT
        }
    }

    fun vkLogOut() {
        VK.logout()
        vkConnectionStateMLive.value = STATE_LOGGED_OUT
        setUserId(0)
    }

    fun getFiveRandomFriends(users: List<VKUser>): List<VKUser> {
        return if (users.isNotEmpty()) {
            val list: MutableList<VKUser> = users.toMutableList()
            val result: MutableList<VKUser> = ArrayList()
            for (i in 1..5) {
                val position = Random.nextInt(0, list.size)
                result.add(list[position])
                list.remove(list[position])
            }
            result.toList()
        } else {
            users
        }
    }

}