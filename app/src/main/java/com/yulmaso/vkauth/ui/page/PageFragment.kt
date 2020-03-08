package com.yulmaso.vkauth.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yulmaso.vkauth.R
import com.yulmaso.vkauth.base.BaseFragment
import com.yulmaso.vkauth.databinding.FragmentPageBinding
import com.yulmaso.vkauth.di.injectViewModel
import com.yulmaso.vkauth.util.*
import javax.inject.Inject

//TODO: handles back button incorrectly
class PageFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : PageViewModel

    private val signalsObserver = Observer<Signal> {
        when(it.signature){
            viewModel.signature -> {
                when (it.command) {

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

        val binding = DataBindingUtil.inflate<FragmentPageBinding>(
            inflater, R.layout.fragment_page, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //init user info
        viewModel.profileInfo.observe(viewLifecycleOwner, Observer {
            binding.profileName = it.firstName + " " + it.lastName
            binding.image = it.photo
        })

        //init friends list
        val adapter = FriendListAdapter()
        binding.friendListRV.adapter = adapter
        viewModel.friends.observe(viewLifecycleOwner, Observer {
            adapter.setUsers(it)
        })

        viewModel.vkConnectionState.observe(viewLifecycleOwner, Observer{
            when(it){
                STATE_LOGGED_OUT -> navigateToAuth()
                STATE_ERROR -> reportRequestFailed()
            }
        })

        setupSignalsObserver(signalsObserver, viewModel)

        return binding.root
    }

    //TODO: throws 'navigation destination is unknown to this NavController'
    private fun navigateToAuth() =
        findNavController().navigate(R.id.action_pageFragment_to_authFragment)

    private fun reportRequestFailed() =
        Toast.makeText(context, R.string.request_failed, Toast.LENGTH_SHORT).show()


}