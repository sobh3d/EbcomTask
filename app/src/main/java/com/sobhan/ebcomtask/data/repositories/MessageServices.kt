package com.sobhan.ebcomtask.data.repositories


import com.sobhan.ebcomtask.data.model.MessagesResponseWrapper
import retrofit2.Response
import retrofit2.http.GET

interface MessageServices {
    @GET("729e846c-80db-4c52-8765-9a762078bc82")
    suspend fun getMessages(
    ): Response<MessagesResponseWrapper>
}