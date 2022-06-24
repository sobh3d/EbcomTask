package com.sobhan.ebcomtask.data.repositories.message


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.sobhan.ebcomtask.data.utils.ApiResult
import com.sobhan.ebcomtask.data.utils.Result
import com.sobhan.ebcomtask.model.Message
import com.sobhan.ebcomtask.ui.utils.DispatcherProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageDataSingleSourceOfTruth @Inject constructor(
    private val messageApiDataSource: MessageApiDataSource,
    private val messagePersistentDataSource: MessagePersistentDataSource,
    private val dispatcher: DispatcherProvider
) {
    fun fetchMessages(): LiveData<Result<MessageResult>> =
        liveData(dispatcher.ui()) {
            emit(Result.Loading)
            apiRequest()


        }



    fun updateMessage(message: Message):LiveData<Result<MessageResult>> =
        liveData(dispatcher.ui()){
            emit(Result.Loading)
            messagePersistentDataSource.updateMessage(message)
            val source = messagePersistentDataSource.getMessagesInstantly()
            if(source is Result.Success){
                emit(Result.Success(MessageResult(source.data)))
            }
        }


    fun deleteMessage(message: Message):LiveData<Result<MessageResult>> =
        liveData(dispatcher.ui()){
            emit(Result.Loading)
            messagePersistentDataSource.deleteMessage(message)
            val source = messagePersistentDataSource.getMessagesInstantly()
            if(source is Result.Success){
                emit(Result.Success(MessageResult(source.data)))
            }
        }



    fun getBookmarkedMessages():LiveData<Result<MessageResult>> =
        liveData(dispatcher.ui()){
            emit(Result.Loading)
            val source = messagePersistentDataSource.getBookmarkedInstantly()
            if(source is Result.Success){
                emit(Result.Success(MessageResult(source.data)))
            }
        }

    private suspend fun LiveDataScope<Result<MessageResult>>.apiRequest(
    ) {
        when (val apiRes = messageApiDataSource.getMessages()) {
            is ApiResult.Success -> {
                val data = apiRes.data
                if (data != null) {
                    val messages = data.messagesResponse
                    when {
                        messages.isEmpty().not() -> {
                            messagePersistentDataSource.dropAndInsertAllMessage(messages)
                            val source = messagePersistentDataSource.getMessagesInstantly()
                            if(source is Result.Success){
                                emit(Result.Success(MessageResult(source.data)))
                            }


                        }
                    }

                }
            }
            is ApiResult.Error -> {
                val result = Result.Error(apiRes.error)
                emit(result)
            }
        }
    }


    data class MessageResult(
        val result: List<Message>,
    )



}