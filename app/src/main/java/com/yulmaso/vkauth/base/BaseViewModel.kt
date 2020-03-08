package com.yulmaso.vkauth.base

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.ArraySet
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yulmaso.vkauth.util.*
import com.yulmaso.vkauth.util.Commands.LOAD
import com.yulmaso.vkauth.util.Commands.SIT_IDLE
import com.yulmaso.vkauth.util.Commands.STOP_LOADING
import com.yulmaso.vkauth.util.Signal.Companion.FLAG_CAUSES_NAVIGATION
import com.yulmaso.vkauth.util.Signal.Companion.FLAG_REQUIRES_LOADING
import java.util.*

abstract class BaseViewModel(application: Application): AndroidViewModel(application) {

    val signature: String = this::class.java.name
    private var isWaitingForAcknowledgement: Boolean = false

    private val signalsQueue = LinkedList<Signal>() //queue the viewmodel emits
    private val signalsEmitterMLive = MutableLiveData<Signal>()
    val signalsEmitter: LiveData<Signal> = signalsEmitterMLive //fragment observes this

    open fun acknowledgeSignal(signal: Signal){
        if (signal.flags.contains(FLAG_CAUSES_NAVIGATION)){
            isWaitingForAcknowledgement = false
            signalsQueue.clear()
            signalsEmitterMLive.value = Signal(SIT_IDLE, signature, ArraySet())
        } else if (signal.signature == signature)
            pollNext()
    }

    protected fun enqueueCommand(command: String, vararg flags: Int) {
        val flagsSet = flags.toCollection(ArraySet())
        if (flagsSet.contains(FLAG_REQUIRES_LOADING)) {
            signalsQueue.add(Signal(LOAD, signature, flagsSet))
            signalsQueue.add(Signal(command, signature, flagsSet))
            signalsQueue.add(Signal(STOP_LOADING, signature, flagsSet))
        } else {
            signalsQueue.add(Signal(command, signature, flagsSet))
        }
        if (!isWaitingForAcknowledgement) {
            isWaitingForAcknowledgement = true
            pollNext()
        }
    }

    private fun pollNext() {
        if (signalsQueue.isEmpty()) {
            isWaitingForAcknowledgement = false
        } else
            signalsEmitterMLive.postValue(signalsQueue.poll())
    }

}