package com.yulmaso.vkauth.ui.page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.yulmaso.vkauth.R
import com.yulmaso.vkauth.base.BaseFragment
import com.yulmaso.vkauth.databinding.FragmentPageBinding
import com.yulmaso.vkauth.di.injectViewModel
import com.yulmaso.vkauth.util.*
import javax.inject.Inject

class PageFragment: BaseFragment() {

    private lateinit var viewModel : PageViewModel

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        navController = findNavController()

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

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun navigateToAuth() =
        navController.navigate(R.id.action_global_authFragment2)

    private fun reportRequestFailed() =
        Toast.makeText(context, R.string.request_failed, Toast.LENGTH_SHORT).show()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_refresh -> viewModel.refresh()
            R.id.item_exit -> viewModel.vkLogOut()
        }

        return super.onOptionsItemSelected(item)
    }

}