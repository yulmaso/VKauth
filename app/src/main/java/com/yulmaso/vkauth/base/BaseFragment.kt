package com.yulmaso.vkauth.base

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yulmaso.vkauth.util.LOG_TAG
import com.yulmaso.vkauth.util.Signal
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

/**Функционал получения команд от вьюмодели*/
abstract class BaseFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //очередь полученных сигналов (возможность получать сигналы от нескольких вьюмоделей)
    private val signalsQueue = LinkedList<Signal>()

    //сюда по очереди помещаются сигналы на обработку
    private val internalSignalsEmitterMLive = MutableLiveData<Signal>()
    private val internalSignalsEmitter: LiveData<Signal> = internalSignalsEmitterMLive

    private var isProcessing: Boolean = false

    //обзервер, получающий команды от вьюмодели
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

    //здесь подключаем получающий обзервер к вьюмодели, и выполняющий обзервер к очереди на обработку
    protected fun setupSignalsObserver(
        signalsObserver: Observer<Signal>,
        viewModel: BaseViewModel
    ) {
        internalSignalsEmitter.observe(viewLifecycleOwner, signalsObserver)
        viewModel.signalsEmitter.observe(viewLifecycleOwner, externalSignalsObserver)
    }
}

