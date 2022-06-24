package com.sobhan.ebcomtask.data.persistent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sobhan.ebcomtask.data.persistent.messages.MessageDao
import com.sobhan.ebcomtask.model.Message

@Database(
    entities = [Message::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        private const val DATABASE_NAME: String = "Message"

        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (DB_INSTANCE == null) {
                        DB_INSTANCE =
                            Room
                                .databaseBuilder(
                                    context.applicationContext,
                                    AppDatabase::class.java,
                                    DATABASE_NAME
                                )
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return DB_INSTANCE!!
        }
    }
}