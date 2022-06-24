package com.sobhan.ebcomtask.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sobhan.ebcomtask.model.Message

data class MessagesResponseWrapper(
    @SerializedName("messages")
    @Expose
    val messagesResponse: List<Message>
)
