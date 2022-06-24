package com.sobhan.ebcomtask.model

import android.security.keystore.StrongBoxUnavailableException
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Nullable

@Entity(tableName = "Message")
data class Message(

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String,

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var description: String,

    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    @Nullable
    var image: String?=null,

    @PrimaryKey
    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    var id: String,

    @SerializedName("unread")
    @Expose
    @ColumnInfo(name = "unread")
    var unread: Boolean=false,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false



    )
