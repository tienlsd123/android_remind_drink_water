package com.bxt.reminddrinkwater.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepository @Inject constructor(
    private val messageDao: MessageDao
) {

    suspend fun getMessages() = messageDao.getAll()

    suspend fun insertMessage(message: Message) = messageDao.insert(message)

    suspend fun updateMessage(message: Message) = messageDao.update(message)

    suspend fun deleteMessage(message: Message) = messageDao.delete(message)
}
