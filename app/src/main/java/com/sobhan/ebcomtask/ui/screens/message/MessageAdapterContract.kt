package com.sobhan.ebcomtask.ui.screens.message

import com.sobhan.ebcomtask.model.Message


interface MessageAdapterContract {
    fun bookmarkMessage(message: Message,clicked:Boolean)
}