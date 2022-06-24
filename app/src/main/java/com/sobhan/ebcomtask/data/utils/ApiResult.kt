package com.sobhan.ebcomtask.data.utils

import com.sobhan.ebcomtask.model.Error

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?) : ApiResult<T>()
    data class Error(
        val error: com.sobhan.ebcomtask.model.Error
    ) : ApiResult<Nothing>()

    companion object {
        const val FAILED_ERROR_CODE = 600
    }
}
