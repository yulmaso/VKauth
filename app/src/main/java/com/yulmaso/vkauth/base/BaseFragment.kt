package com.yulmaso.vkauth.base

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.util.Signal
import dagger.android.support.DaggerFragment
import java.util.*

abstract class BaseFragment: DaggerFragment() {

    //queue where fragment stores received signals
    private val signalsQueue = LinkedList<Signal>()

    private val internalSignalsEmitterMLive = MutableLiveData<Signal>()
    private val internalSignalsEmitter: LiveData<Signal> = internalSignalsEmitterMLive

    private var isProcessing: Boolean = false

    //fragment receives commands from viewmodels here
    private val externalSignalsObserver = Observer<Signal> { signal ->
        signalsQueue.add(signal)
        if (!isProcessing) {
            isProcessing = true
            resume()
        }
    }

    fun resume() {
        if (signalsQueue.isEmpty())
            isProcessing = false
        else {
            internalSignalsEmitterMLive.postValue(signalsQueue.poll())
        }
    }

    protected fun setupSignalsObserver(
        signalsObserver: Observer<Signal>,
        viewModel: BaseViewModel
    ) {
        internalSignalsEmitter.observe(viewLifecycleOwner, signalsObserver)
        viewModel.signalsEmitter.observe(viewLifecycleOwner, externalSignalsObserver)
    }
}

