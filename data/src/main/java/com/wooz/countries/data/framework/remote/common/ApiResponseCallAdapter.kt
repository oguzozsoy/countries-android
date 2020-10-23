package com.wooz.countries.data.framework.remote.common

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * @author wooz
 * @since 10/10/2020
 */
class ApiResponseCallAdapter<E>(private val responseType: Type) :
    CallAdapter<E, Call<ApiResponse<E>>> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<E>): Call<ApiResponse<E>> {
        return ApiResponseCall(call)
    }

    internal class ApiResponseCall<E>(private val delegate: Call<E>) : Call<ApiResponse<E>> {
        override fun clone(): Call<ApiResponse<E>> = ApiResponseCall(delegate.clone())


        override fun execute(): Response<ApiResponse<E>> {
            throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
        }

        override fun isExecuted(): Boolean = delegate.isExecuted

        override fun cancel() = delegate.cancel()

        override fun isCanceled(): Boolean = delegate.isCanceled

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()


        override fun enqueue(callback: Callback<ApiResponse<E>>) {
            return delegate.enqueue(object : Callback<E> {
                override fun onResponse(call: Call<E>, response: Response<E>) {
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse.create(response))
                    )
                }

                override fun onFailure(call: Call<E>, t: Throwable) {
                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse.create(t))
                    )
                }

            })
        }
    }
}