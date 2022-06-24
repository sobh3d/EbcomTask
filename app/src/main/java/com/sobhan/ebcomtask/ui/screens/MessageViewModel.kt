package com.sobhan.ebcomtask.ui.screens


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sobhan.ebcomtask.data.repositories.message.MessageDataSingleSourceOfTruth
import com.sobhan.ebcomtask.data.utils.Result
import com.sobhan.ebcomtask.model.Error
import com.sobhan.ebcomtask.model.Message
import com.sobhan.ebcomtask.ui.utils.BaseViewModel
import com.sobhan.ebcomtask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private var messageDataSingleSourceOfTruth: MessageDataSingleSourceOfTruth
) : BaseViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages
    private val messageList: MutableList<Message> = ArrayList()

    private val _bookmarkMessages = MutableLiveData<List<Message>>()
    val bookmarkMessages: LiveData<List<Message>> = _bookmarkMessages
    private val bookmarkList: MutableList<Message> = ArrayList()

    private val _unreadMessagesCount = MutableLiveData<Int>()
    var unreadMessagesCount: LiveData<Int> = _unreadMessagesCount
    private var count: Int = 0

    var error: SingleLiveEvent<Error?> = SingleLiveEvent()
    var noData: SingleLiveEvent<Boolean?> = SingleLiveEvent()


    init {

        observe(
            messageDataSingleSourceOfTruth.fetchMessages(),
            Observer {
                when (it) {
                    is Result.Success -> {
                        messageList.clear()
                        messageList.addAll(it.data.result)
                        _messages.value = ArrayList(messageList)
                    }
                    is Result.Error -> {
                        error.value = it.error
                        if (messageList.isEmpty()) {
                            messageList.clear()
                            _messages.value = null
                        }
                    }

                    is Result.Empty -> {
                        messageList.clear()
                        _messages.value = null
                    }

                    else -> {
                    }
                }
            }
        )

        observe(
            messageDataSingleSourceOfTruth.getBookmarkedMessages(),
            Observer {
                when (it) {
                    is Result.Success -> {
                        bookmarkList.clear()
                        bookmarkList.addAll(it.data.result)
                        _bookmarkMessages.value = ArrayList(bookmarkList)
                    }
                    is Result.Error -> {
                        error.value = it.error
                        if (bookmarkList.isEmpty()) {
                            bookmarkList.clear()
                            _bookmarkMessages.value = null
                        }
                    }

                    is Result.Empty -> {
                        bookmarkList.clear()
                        _bookmarkMessages.value = null
                    }

                    else -> {
                        bookmarkList.clear()
                        _bookmarkMessages.value = null
                    }
                }
            }
        )


    }


    fun update(message: Message) {
        observe(
            messageDataSingleSourceOfTruth.updateMessage(message),
            Observer {
                when (it) {
                    is Result.Success -> {
                        messageList.clear()
                        messageList.addAll(it.data.result)
                        _messages.value = ArrayList(messageList)
                        getBookmarkedMessages()
                    }
                    is Result.Error -> {
                        error.value = it.error
                        if (messageList.isEmpty()) {
                            messageList.clear()
                            _messages.value = null
                        }
                    }

                    is Result.Empty -> {
                        messageList.clear()
                        _messages.value = null
                    }

                    else -> {
                        bookmarkList.clear()
                        _bookmarkMessages.value = null
                    }
                }
            }
        )
    }

    fun deleteMessage(message: Message) {
        observe(
            messageDataSingleSourceOfTruth.deleteMessage(message),
            Observer {
                when (it) {
                    is Result.Success -> {
                        messageList.clear()
                        messageList.addAll(it.data.result)
                        _messages.value = ArrayList(messageList)
                        getBookmarkedMessages()
                    }
                    is Result.Error -> {
                        error.value = it.error
                        if (messageList.isEmpty()) {
                            messageList.clear()
                            _messages.value = null
                        }
                    }

                    is Result.Empty -> {
                        messageList.clear()
                        _messages.value = null
                    }

                    else -> {
                        messageList.clear()
                        _messages.value = null
                    }
                }
            }
        )
    }

    fun getBookmarkedMessages() {
        observe(
            messageDataSingleSourceOfTruth.getBookmarkedMessages(),
            Observer {
                when (it) {
                    is Result.Success -> {
                        bookmarkList.clear()
                        bookmarkList.addAll(it.data.result)
                        _bookmarkMessages.value = ArrayList(bookmarkList)
                    }
                    is Result.Error -> {
                        error.value = it.error
                        if (bookmarkList.isEmpty()) {
                            bookmarkList.clear()
                            _bookmarkMessages.value = null
                        }
                    }

                    is Result.Empty -> {
                        bookmarkList.clear()
                        _bookmarkMessages.value = null
                    }

                    else -> {
                        bookmarkList.clear()
                        _bookmarkMessages.value = null
                    }
                }
            }
        )
    }


    fun unreadMessageCount(totalCount: Int) {
        count = totalCount
        _unreadMessagesCount.value = count
    }

    fun decreaseUnreadMessageCount() {
        count -= 1
        _unreadMessagesCount.value = count
    }


}