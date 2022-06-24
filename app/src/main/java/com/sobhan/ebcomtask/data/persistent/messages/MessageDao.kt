package com.sobhan.ebcomtask.data.persistent.messages


import androidx.room.*
import com.sobhan.ebcomtask.data.persistent.BaseDao
import com.sobhan.ebcomtask.model.Message


@Dao
interface MessageDao : BaseDao<Message> {



    @Query("SELECT * FROM Message")
    suspend fun getAllInstantly(): List<Message>



    @Query("SELECT * FROM Message WHERE bookmarked = :bookmarked ORDER BY CASE WHEN unread = 0 THEN unread = 1 END")
    suspend fun getAllBookmarkedInstantly(bookmarked:Boolean): List<Message>

    @Query("DELETE FROM Message")
    suspend fun clear()

    @Update
    suspend fun updateMessage(message: Message)


    @Delete
    suspend fun deleteMessage(message: Message)



    @Transaction
    suspend fun dropAndInsertAllMessage(messages: List<Message>) {
        clear()
        insertAll(messages)
    }
}