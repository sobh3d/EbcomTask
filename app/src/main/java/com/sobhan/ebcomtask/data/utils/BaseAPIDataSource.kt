package com.sobhan.ebcomtask.data.utils

import com.sobhan.ebcomtask.ui.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseAPIDataSource(
    private val apiErrorHandler: ApiErrorHandler,
    private val dispatcher: DispatcherProvider
) {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): ApiResult<T> =
        withContext(dispatcher.io()) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    ApiResult.Success(
                        response.body()
                    )
                } else {
                    apiErrorHandler.onError(response)
                }
            } catch (e: Exception) {
                apiErrorHandler.onFailed(e)
            }
        }
}