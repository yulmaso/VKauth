package com.yulmaso.vkauth.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.yulmaso.vkauth.R
import com.yulmaso.vkauth.di.injectViewModel
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.util.STATE_ERROR
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = injectViewModel(viewModelFactory)

        navController = findNavController(R.id.nav_host_fragment)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setLogo(R.drawable.ic_vk_social_network_logo_white)

        //чтобы на экране аутентификации не было тулбара
        nav_host_fragment.findNavController().addOnDestinationChangedListener {
            _, destination, _ -> when (destination.id) {
            R.id.authFragment2 -> toolbar.visibility = View.GONE
            R.id.pageFragment2 -> {
                toolbar.visibility = View.VISIBLE
            }
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Log.d(LOG_TAG, "auth success, userID: " + token.userId)

                viewModel.setUserId(token.userId)
                viewModel.checkVkLogin()
            }
            override fun onLoginFailed(errorCode: Int) {
                Log.d(LOG_TAG, "auth failed")
                viewModel.setVkConnectionState(STATE_ERROR)
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
