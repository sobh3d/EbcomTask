package com.sobhan.ebcomtask.data.repositories.message

import com.sobhan.ebcomtask.data.persistent.messages.MessageDao
import com.sobhan.ebcomtask.data.utils.Result
import com.sobhan.ebcomtask.model.Message
import com.sobhan.ebcomtask.ui.utils.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagePersistentDataSource @Inject constructor(
    private val messageDao: MessageDao,
    private val dispatcher: DispatcherProvider
) {

    suspend fun getMessagesInstantly(): Result<List<Message>> =
        withContext(dispatcher.io()) {
            val data = messageDao.getAllInstantly()
            if (data.isEmpty().not()) {
                Result.Success(data)
            } else {
                Result.Empty
            }
        }

    suspend fun getBookmarkedInstantly(): Result<List<Message>> =
        withContext(dispatcher.io()) {
            val data = messageDao.getAllBookmarkedInstantly(true)
            if (data.isEmpty().not()) {
                Result.Success(data)
            } else {
                Result.Empty
            }
        }


    suspend fun updateMessage(message: Message) {
        withContext(dispatcher.io()) {
            messageDao.updateMessage(message)
        }
    }

    suspend fun deleteMessage(message: Message) {
        withContext(dispatcher.io()) {
            messageDao.deleteMessage(message)
        }
    }


    suspend fun dropAndInsertAllMessage(messages: List<Message>) =
        withContext(dispatcher.io()) {
            messageDao.dropAndInsertAllMessage(messages)
        }


}