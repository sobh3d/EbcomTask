package com.sobhan.ebcomtask.data.utils

import android.content.Context
import com.sobhan.ebcomtask.BuildConfig
import com.sobhan.ebcomtask.R
import com.sobhan.ebcomtask.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.sobhan.ebcomtask.model.Error

@Singleton
class ApiErrorHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun onFailed(ex: Exception): ApiResult.Error {
        val message =
            when {
                context.isNetworkAvailable().not() -> {
                    R.string.error_no_internet
                }
                ex is HttpException -> {
                    R.string.error_server_error
                }
                else -> {
                    R.string.error_unknown
                }
            }

        return ApiResult.Error(
            Error(
                ApiResult.FAILED_ERROR_CODE,
                getString(message)
            )
        )
    }

    fun <T> onError(response: Response<T>): ApiResult<T> {
        val code = response.code()

        if (context.isNetworkAvailable().not()) {
            return ApiResult.Error(
                Error(
                    code,
                    getString(R.string.error_no_internet)
                )
            )
        }

        val responseMessage =
            if (BuildConfig.DEBUG) {
                response.errorBody()?.toString() ?: getString(R.string.error_unknown)
            } else {
                getString(R.string.error_unknown)
            }

        return ApiResult.Error(
            Error(
                code,
                responseMessage
            )
        )
    }

    private fun getString(text: Int) =
        context.resources.getString(text)
}