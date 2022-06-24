package com.sobhan.ebcomtask.data.utils




sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Empty : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: com.sobhan.ebcomtask.model.Error) : Result<Nothing>()


}
