package com.wooz.countries.data.repository.common

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.wooz.countries.data.framework.remote.common.ApiErrorResponse
import com.wooz.countries.data.framework.remote.common.ApiResponse
import com.wooz.countries.data.framework.remote.common.ApiSuccessResponse
import com.wooz.countries.domain.entity.ResultData
import kotlinx.coroutines.flow.*

/**
 * @author wooz
 * @since 10/10/2020
 */
abstract class NetworkBoundResource<T> {
    companion object{
        private const val TAG = "NetworkBoundResource"
    }
    fun asFlow() = flow {
        emit(ResultData.Loading())
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(ResultData.Loading())
            when (val apiResponse = createCall()) {
                is ApiSuccessResponse -> {
                    saveCallResult(processResponse(apiResponse))
                    emitAll(loadFromDb().map { ResultData.Success(it) })
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    Log.i(TAG, "asFlow: ${apiResponse.errorMessage}")
                    emitAll(loadFromDb().map { ResultData.Failed(apiResponse.errorMessage) })
                }
            }
        }else{
            emitAll(loadFromDb().map { ResultData.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<T>) = response.body

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: T)

    @MainThread
    protected abstract fun shouldFetch(data: T?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<T>

    @MainThread
    protected abstract suspend fun createCall(): ApiResponse<T>
}