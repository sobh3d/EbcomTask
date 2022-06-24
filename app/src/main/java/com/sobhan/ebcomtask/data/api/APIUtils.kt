package com.sobhan.ebcomtask.data.api

import com.sobhan.ebcomtask.BuildConfig
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object APIUtils {
    const val API_BASE_URL = "https://run.mocky.io/v3/"

    fun getLoggingInterceptor(): Interceptor? {
        if (BuildConfig.DEBUG.not()) {
            return null
        }

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return logger
    }
}