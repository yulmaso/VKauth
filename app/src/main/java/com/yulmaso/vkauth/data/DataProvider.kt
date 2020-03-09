package com.yulmaso.vkauth.data

import android.content.SharedPreferences
import com.yulmaso.vkauth.util.VK_USER_ID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataProvider @Inject constructor(
    private val authPreferences: SharedPreferences
) {

    fun setUserId(userId: Int) = authPreferences.edit().putInt(VK_USER_ID, userId).apply()

    fun getUserId() = authPreferences.getInt(VK_USER_ID, 0)

}