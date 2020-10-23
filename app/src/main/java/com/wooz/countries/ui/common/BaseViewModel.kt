package com.wooz.countries.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wooz.countries.domain.entity.ResultData

/**
 * @author wooz
 * @since 09/10/2020
 */
open class BaseViewModel : ViewModel() {

    val loadingErrorState = MutableLiveData<ResultData<Any>>()

    fun handleTask(task: ResultData<Any>, callback: () -> Unit = {}) {
        loadingErrorState.postValue(task)
        callback.invoke()
    }
}