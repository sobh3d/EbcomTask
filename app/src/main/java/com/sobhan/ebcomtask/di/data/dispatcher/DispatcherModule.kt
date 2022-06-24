package com.sobhan.ebcomtask.di.data.dispatcher

import com.sobhan.ebcomtask.ui.utils.DefaultDispatcher
import com.sobhan.ebcomtask.ui.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Singleton
    fun providerDispatcher(): DispatcherProvider {
        return DefaultDispatcher()
    }
}