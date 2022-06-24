package com.sobhan.ebcomtask.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val observingLiveDataList = HashSet<LiveDataAndObserver<*>>()



    fun <T : Any?> observe(liveData: LiveData<T>, observer: Observer<T>) {
        observingLiveDataList.add(
            LiveDataAndObserver(
                liveData,
                observer
            )
        )
    }

    override fun onCleared() {
        for (liveDataAndObserver in observingLiveDataList) {
            liveDataAndObserver.stopObserving()
        }

        super.onCleared()
    }

    private class LiveDataAndObserver<T>(
        val liveData: LiveData<T>,
        val observer: Observer<T>
    ) {
        init {
            liveData.observeForever(observer)
        }

        fun stopObserving() {
            liveData.removeObserver(observer)
        }
    }
}