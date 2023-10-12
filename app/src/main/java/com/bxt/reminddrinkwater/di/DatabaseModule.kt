package com.bxt.reminddrinkwater.di

import android.content.Context
import com.bxt.reminddrinkwater.data.AppDatabase
import com.bxt.reminddrinkwater.data.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()
}
