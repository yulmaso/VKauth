package com.yulmaso.vkauth.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.yulmaso.vkauth.R
import com.yulmaso.vkauth.data.Repository
import com.yulmaso.vkauth.ui.auth.AuthViewModel
import com.yulmaso.vkauth.util.AUTH_PREFERENCES
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.util.VK_USER_ID
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                Log.d(LOG_TAG, "auth success, userID: " + token.userId)

                getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
                    .edit().putInt(VK_USER_ID, token.userId).apply()

                repository.checkVkLogin()
            }

            override fun onLoginFailed(errorCode: Int) {
                Log.d(LOG_TAG, "auth fail")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
