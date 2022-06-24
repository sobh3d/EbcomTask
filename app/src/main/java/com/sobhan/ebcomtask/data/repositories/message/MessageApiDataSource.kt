package com.sobhan.ebcomtask.data.repositories.message

import com.sobhan.ebcomtask.data.repositories.MessageServices
import com.sobhan.ebcomtask.data.utils.ApiErrorHandler
import com.sobhan.ebcomtask.data.utils.BaseAPIDataSource
import com.sobhan.ebcomtask.ui.utils.DispatcherProvider
import javax.inject.Inject

class MessageApiDataSource @Inject constructor(
    private val service: MessageServices,
    errorHandler: ApiErrorHandler,
    dispatcher: DispatcherProvider
) : BaseAPIDataSource(errorHandler, dispatcher) {

    suspend fun getMessages() = getResult {
        service.getMessages()
    }
}