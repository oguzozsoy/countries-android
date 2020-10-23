package com.wooz.countries.data.framework.remote.common

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author wooz
 * @since 10/10/2020
 */
class ApiResponseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ApiResponse<<Foo>> or Call<ApiResponse<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as ApiResponse<Foo> or ApiResponse<out Foo>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return ApiResponseCallAdapter<Any>(successBodyType)

    }
}