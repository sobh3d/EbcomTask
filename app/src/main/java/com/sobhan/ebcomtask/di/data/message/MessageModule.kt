package com.sobhan.ebcomtask.di.data.message

import com.sobhan.ebcomtask.data.api.RetrofitProvider
import com.sobhan.ebcomtask.data.repositories.MessageServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MessageModule {
    @Provides
    @Singleton
    fun provideMessageApiServices(retrofit: Retrofit): MessageServices {
        return RetrofitProvider.provideService(
            retrofit,
            MessageServices::class.java
        )
    }


}