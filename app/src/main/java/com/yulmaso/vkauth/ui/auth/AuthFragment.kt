package com.yulmaso.vkauth.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.yulmaso.vkauth.R
import com.yulmaso.vkauth.base.BaseFragment
import com.yulmaso.vkauth.databinding.FragmentAuthBinding
import com.yulmaso.vkauth.di.injectViewModel
import com.yulmaso.vkauth.ui.MainActivity
import com.yulmaso.vkauth.util.*
import javax.inject.Inject


class AuthFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : AuthViewModel

    //обзервер, выполняющий команды от вьюмодели
    private val signalsObserver = Observer<Signal> {
        when(it.signature){
            viewModel.signature -> {
                when(it.command) {
                    Commands.VK_LOGIN -> vkLogin()
                }
                viewModel.acknowledgeSignal(it)
            }
        }
        resume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = DataBindingUtil.inflate<FragmentAuthBinding>(
            inflater, R.layout.fragment_auth, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupSignalsObserver(signalsObserver, viewModel)

        viewModel.vkConnectionState.observe(viewLifecycleOwner, Observer {
            when (it) {
                STATE_LOGGED_IN -> navigateUp()
                STATE_ERROR -> reportAuthFailed()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    private fun vkLogin(){
        //https://github.com/VKCOM/vk-android-sdk/issues/421 :C
        VK.login(activity as MainActivity, setOf(VKScope.FRIENDS))
    }

    private fun navigateUp() = findNavController().navigateUp()

    private fun reportAuthFailed() =
        Toast.makeText(activity, R.string.auth_failed, Toast.LENGTH_SHORT).show()

}